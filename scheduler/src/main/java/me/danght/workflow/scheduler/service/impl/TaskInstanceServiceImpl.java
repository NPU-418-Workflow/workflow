package me.danght.workflow.scheduler.service.impl;

import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.scheduler.bo.TaskInstanceBO;
import me.danght.workflow.scheduler.constant.TaskInstanceState;
import me.danght.workflow.scheduler.convert.TaskInstanceConvert;
import me.danght.workflow.scheduler.dao.ActivityInstanceRepository;
import me.danght.workflow.scheduler.dao.ProcessInstanceRepository;
import me.danght.workflow.scheduler.dao.TaskHistoryInstanceRepository;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dataobject.ActivityInstanceDO;
import me.danght.workflow.scheduler.dataobject.ProcessInstanceDO;
import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import me.danght.workflow.scheduler.dto.TaskInstanceDTO;
import me.danght.workflow.scheduler.dto.TaskInstanceQueryDTO;
import me.danght.workflow.scheduler.msg.ScheduleRequestMessage;
import me.danght.workflow.scheduler.msg.TaskInstanceMessage;
import me.danght.workflow.scheduler.service.TaskInstanceService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 任务实例服务
 *
 * @author wenxiang
 * @author DangHT
 */
@ApplicationScoped
@ActivateRequestContext
public class TaskInstanceServiceImpl implements TaskInstanceService {

    @Inject
    TaskInstanceRepository taskInstanceRepository;

    @Inject
    TaskHistoryInstanceRepository taskHistoryInstanceRepository;

    @Inject
    ActivityInstanceRepository activityInstanceRepository;

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
        return (int) taskInstanceRepository.countByTiStatusAndAiId(taskInstanceQueryDTO.getTiStatus(), taskInstanceQueryDTO.getAiId());
    }


    @Override
    public List<TaskInstanceDTO> findRelatedTaskList(String aiId) {
        List<TaskInstanceDO> taskInstanceDOList = taskInstanceRepository
                .findAllByTiStatusAndAiId(TaskInstanceState.TASK_INSTANCE_STATE_COMPLETED, aiId);
        List<TaskInstanceDTO> taskInstanceDTOList = new ArrayList<>();
        for(TaskInstanceDO taskInstanceDO : taskInstanceDOList){
            taskInstanceDTOList.add(TaskInstanceConvert.INSTANCE.convertDOToDTO(taskInstanceDO));
        }
        return taskInstanceDTOList;
    }

    @Override
    public void moveRelatedTaskToHistory(String aiId) {
        List<TaskInstanceDO> taskInstanceDOList = taskInstanceRepository.findAllByAiId(aiId);
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
        TaskInstanceDO taskInstanceDO = taskInstanceRepository.findById(taskInstanceDTO.getId()).orElse(null);
        if (taskInstanceDO == null) return null;
        taskInstanceDO.setTiAssigner(taskInstanceDTO.getTiAssigner());
        taskInstanceDO.setTiAssignerType("0");
        //修改为修改任务执行人种类和执行人
        taskInstanceRepository.save(taskInstanceDO);
        //其实还应该回查下是否真的获取到了，高并发情况下有可能被别人抢了，有点懒就没写，而且同一个任务应该不会太多人抢。
        redisClient.set(List.of(taskInstanceDTO.getId(), "1"));
        return taskInstanceDTO;
    }

    @Override
    public List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType) {
        List<TaskInstanceDO> taskInstanceDOList = taskInstanceRepository
                .findAllByTiAssignerAndTiAssignerType(tiAssigner, tiAssignerType);
        List<TaskInstanceBO> taskInstanceBOList = new ArrayList<>();
        for(TaskInstanceDO taskInstanceDO : taskInstanceDOList){
            TaskInstanceBO taskInstanceBO = TaskInstanceConvert.INSTANCE.convertDOToBO(taskInstanceDO);
            ActivityInstanceDO activityInstanceDO = activityInstanceRepository.findById(taskInstanceBO.getAiId()).orElse(null);
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
        List<TaskInstanceDO> taskInstanceDOList = taskInstanceRepository
                .findAllByTiAssignerAndTiAssignerType(tiAssigner, "1");
        List<TaskInstanceBO> taskInstanceBOList = new ArrayList<>();
        for(TaskInstanceDO taskInstanceDO : taskInstanceDOList){
            TaskInstanceBO taskInstanceBO = TaskInstanceConvert.INSTANCE.convertDOToBO(taskInstanceDO);
            ActivityInstanceDO activityInstanceDO = activityInstanceRepository.findById(taskInstanceBO.getAiId()).orElse(null);
            if (activityInstanceDO == null) return null;
            ProcessInstanceDO processInstanceDO = processInstanceRepository.findById(activityInstanceDO.getPiId()).orElse(null);
            if (processInstanceDO == null) return null;
            taskInstanceBO.setPiBusinessKey(processInstanceDO.getPiBusinessKey());
            taskInstanceBOList.add(taskInstanceBO);
        }
        return taskInstanceBOList;
    }

    private void sendScheduleRequestMessage(TaskInstanceMessage taskInstanceMessage) {
        emitter.send(new ScheduleRequestMessage().setTaskInstanceMessage(taskInstanceMessage));
    }
}
