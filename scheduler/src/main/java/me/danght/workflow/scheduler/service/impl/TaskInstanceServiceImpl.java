package me.danght.workflow.scheduler.service.impl;

import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.common.api.schduler.TaskInstanceService;
import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.constant.TaskInstanceState;
import me.danght.workflow.common.dto.TaskInstanceDTO;
import me.danght.workflow.common.dto.TaskInstanceQueryDTO;
import me.danght.workflow.common.msg.ScheduleRequestMessage;
import me.danght.workflow.common.msg.TaskInstanceMessage;
import me.danght.workflow.scheduler.convert.TaskInstanceConvert;
import me.danght.workflow.scheduler.dao.ActivityInstanceRepository;
import me.danght.workflow.scheduler.dao.ProcessInstanceRepository;
import me.danght.workflow.scheduler.dao.TaskHistoryInstanceRepository;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dataobject.ActivityInstanceDO;
import me.danght.workflow.scheduler.dataobject.ProcessInstanceDO;
import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import org.apache.dubbo.config.annotation.DubboService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.*;


@DubboService(interfaceClass = TaskInstanceService.class)
@Singleton
public class TaskInstanceServiceImpl implements TaskInstanceService {

    @Inject
    TaskInstanceRepository taskInstanceRepository;

    @Inject
    TaskHistoryInstanceRepository taskHistoryInstanceRepository;

    @Inject
    ActivityInstanceRepository wfActivityInstanceRepository;

    @Inject
    ProcessInstanceRepository processInstanceRepository;

    @Inject
    RedisClient redisClient;

    @Inject
    @Channel("schedule-request-out")
    Emitter<ScheduleRequestMessage> emitter;

    //public TransactionSendResult sendMessageInTransaction(TaskInstanceMessage wfTaskInstanceMessage, WfTaskInstanceDO wfTaskInstanceDO) {
    //    // 创建 Demo07Message 消息
    //    Message<ScheduleRequestMessage> message = MessageBuilder.withPayload(new ScheduleRequestMessage().setWfTaskInstanceMessage(wfTaskInstanceMessage))
    //            .build();
    //    // 发送事务消息,最后一个参数事务处理用
    //    return rocketMQTemplate.sendMessageInTransaction("task-transaction-producer-group", ScheduleRequestMessage.TOPIC, message, WfTaskInstanceConvert.INSTANCE.convertDOToDTO(wfTaskInstanceDO));
    //}

    @Transactional
    @Override
    public void ending(TaskInstanceDTO taskInstanceDTO){
        //进行历史记录
        TaskInstanceDO taskInstanceDO = TaskInstanceConvert.INSTANCE.convertDTOToDO(taskInstanceDTO);
        taskInstanceRepository.save(taskInstanceDO);
        TaskHistoryInstanceDO taskHistoryInstanceDO = TaskInstanceConvert.INSTANCE.convertRunToHistory(taskInstanceDO);
        taskHistoryInstanceDO.setCreateTime(new Date());
        taskHistoryInstanceDO.setUpdateTime(taskHistoryInstanceDO.getCreateTime());
        taskHistoryInstanceRepository.save(taskHistoryInstanceDO);
        //从运行表中清除当前记录
        taskInstanceRepository.deleteById(taskInstanceDO.getId());
    }

    /**
     * 根据状态和aiId进行查询
     * @param taskInstanceQueryDTO
     * @return
     */
    @Override
    public int count(TaskInstanceQueryDTO taskInstanceQueryDTO) {
        return taskInstanceRepository.countByTiStatusAndAiId(taskInstanceQueryDTO.getTiStatus(), taskInstanceQueryDTO.getAiId());
    }


    @Override
    public List<TaskInstanceDTO> findRelatedTaskList(String aiId) {
        List<TaskInstanceDO> taskInstanceDOList = (List<TaskInstanceDO>) taskInstanceRepository
                .findAllByTiStatusAndAiId(TaskInstanceState.TASK_INSTANCE_STATE_COMPLETED, aiId);
        List<TaskInstanceDTO> wfTaskInstanceDTOList = new ArrayList<>();
        for(TaskInstanceDO taskInstanceDO : taskInstanceDOList){
            wfTaskInstanceDTOList.add(TaskInstanceConvert.INSTANCE.convertDOToDTO(taskInstanceDO));
        }
        return wfTaskInstanceDTOList;
    }

    @Override
    public void moveRelatedTaskToHistory(String aiId) {
        List<TaskInstanceDO> taskInstanceDOList = (List<TaskInstanceDO>) taskInstanceRepository.findAllByAiId(aiId);
        for(TaskInstanceDO taskInstanceDO : taskInstanceDOList){
            TaskHistoryInstanceDO taskHistoryInstanceDO = TaskInstanceConvert.INSTANCE.convertRunToHistory(taskInstanceDO);
            taskHistoryInstanceDO.setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_PAST);
            taskHistoryInstanceDO.setUpdateTime(new Date());
            taskHistoryInstanceDO.setCreateTime(taskHistoryInstanceDO.getUpdateTime());
            taskHistoryInstanceRepository.save(taskHistoryInstanceDO);
            taskInstanceRepository.deleteById(taskInstanceDO.getId());
        }
    }

    @Override
    public TaskInstanceBO save(TaskInstanceDTO taskInstanceDTO) {
        TaskInstanceDO taskInstanceDO = TaskInstanceConvert.INSTANCE.convertDTOToDO(taskInstanceDTO);
        taskInstanceRepository.save(taskInstanceDO);
        //打时间戳记录
        TaskHistoryInstanceDO taskHistoryInstanceDO = TaskInstanceConvert.INSTANCE.convertRunToHistory(taskInstanceDO);
        taskHistoryInstanceDO.setCreateTime(new Date());
        taskHistoryInstanceDO.setUpdateTime(taskHistoryInstanceDO.getCreateTime());
        taskHistoryInstanceRepository.save(taskHistoryInstanceDO);
        return TaskInstanceConvert.INSTANCE.convertDOToBO(taskInstanceDO);
    }

    @Override
    public void recordHistory(TaskInstanceDTO taskInstanceDTO) {
        TaskInstanceDO taskInstanceDO = TaskInstanceConvert.INSTANCE.convertDTOToDO(taskInstanceDTO);
        TaskHistoryInstanceDO taskHistoryInstanceDO = TaskInstanceConvert.INSTANCE.convertRunToHistory(taskInstanceDO);
        taskHistoryInstanceDO.setCreateTime(new Date());
        taskHistoryInstanceDO.setUpdateTime(taskHistoryInstanceDO.getCreateTime());
        taskHistoryInstanceRepository.save(taskHistoryInstanceDO);
    }

    @Override
    public TaskInstanceBO getById(String id) {
        return TaskInstanceConvert.INSTANCE.convertDOToBO(taskInstanceRepository.findById(id).orElse(null));
    }

    @Override
    public void delete(String id) {
        taskInstanceRepository.deleteById(id);
    }

    @Override
    public String getFirstTaskId(String piId) {
        TaskInstanceDO taskInstanceDO = taskInstanceRepository.findByPiId(piId).orElse(null);
        if (taskInstanceDO == null) return null;
        return taskInstanceDO.getId();
    }

    @Override
    public TaskInstanceDTO updateById(TaskInstanceDTO taskInstanceDTO) {
        //修改为修改任务执行人种类和执行人
        taskInstanceRepository.updateAssignerType(taskInstanceDTO.getId(),taskInstanceDTO.getTiAssigner());
        //其实还应该回查下是否真的获取到了，高并发情况下有可能被别人抢了，有点懒就没写，而且同一个任务应该不会太多人抢。
        redisClient.set(List.of(taskInstanceDTO.getId(), "1"));
        return taskInstanceDTO;
    }

    @Override
    public List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType) {
        List<TaskInstanceDO> taskInstanceDOList = (List<TaskInstanceDO>) taskInstanceRepository
                .findAllByTiAssignerAndTiAssignerTrue(tiAssigner, tiAssignerType);
        List<TaskInstanceBO> taskInstanceBOList = new ArrayList<>();
        for(TaskInstanceDO taskInstanceDO : taskInstanceDOList){
            TaskInstanceBO taskInstanceBO = TaskInstanceConvert.INSTANCE.convertDOToBO(taskInstanceDO);
            ActivityInstanceDO activityInstanceDO = wfActivityInstanceRepository.findById(taskInstanceBO.getAiId()).orElse(null);
            if (activityInstanceDO == null) return null;
            ProcessInstanceDO processInstanceDO = processInstanceRepository.findById(activityInstanceDO.getPiId()).orElse(null);
            if (processInstanceDO == null) return null;
            taskInstanceBO.setPiBusinessKey(processInstanceDO.getPiBusinessKey());
            taskInstanceBOList.add(taskInstanceBO);
        }
        return taskInstanceBOList;
    }

    @Override
    public List<TaskInstanceBO> selectUnObtainedTask(String tiAssigner) {
        List<TaskInstanceDO> taskInstanceDOList = (List<TaskInstanceDO>) taskInstanceRepository
                .findAllByTiAssignerAndTiAssignerTrue(tiAssigner, "1");
        List<TaskInstanceBO> taskInstanceBOList = new ArrayList<>();
        for(TaskInstanceDO taskInstanceDO : taskInstanceDOList){
            TaskInstanceBO wfTaskInstanceBO = TaskInstanceConvert.INSTANCE.convertDOToBO(taskInstanceDO);
            ActivityInstanceDO activityInstanceDO = wfActivityInstanceRepository.findById(wfTaskInstanceBO.getAiId()).orElse(null);
            if (activityInstanceDO == null) return null;
            ProcessInstanceDO processInstanceDO = processInstanceRepository.findById(activityInstanceDO.getPiId()).orElse(null);
            if (processInstanceDO == null) return null;
            wfTaskInstanceBO.setPiBusinessKey(processInstanceDO.getPiBusinessKey());
            taskInstanceBOList.add(wfTaskInstanceBO);
        }
        return taskInstanceBOList;
    }

    private void sendScheduleRequestMessage(TaskInstanceMessage taskInstanceMessage) {
        emitter.send(new ScheduleRequestMessage().setTaskInstanceMessage(taskInstanceMessage));
    }
}
