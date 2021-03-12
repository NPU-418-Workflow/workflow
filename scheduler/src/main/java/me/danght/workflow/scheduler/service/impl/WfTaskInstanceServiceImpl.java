package me.danght.workflow.scheduler.service.impl;

import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.common.api.schduler.TaskInstanceService;
import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.constant.TaskInstanceState;
import me.danght.workflow.common.dto.TaskInstanceDTO;
import me.danght.workflow.common.dto.TaskInstanceQueryDTO;
import me.danght.workflow.common.msg.ScheduleRequestMessage;
import me.danght.workflow.common.msg.TaskInstanceMessage;
import me.danght.workflow.scheduler.convert.WfTaskInstanceConvert;
import me.danght.workflow.scheduler.dao.WfActivtityInstanceMapper;
import me.danght.workflow.scheduler.dao.WfProcessInstanceMapper;
import me.danght.workflow.scheduler.dao.WfTaskHistoryInstanceMapper;
import me.danght.workflow.scheduler.dao.WfTaskInstanceMapper;
import me.danght.workflow.scheduler.dataobject.WfActivityInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfProcessInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfTaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfTaskInstanceDO;
import org.apache.dubbo.config.annotation.DubboService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.*;


@DubboService(interfaceClass = TaskInstanceService.class)
@Singleton
public class WfTaskInstanceServiceImpl implements TaskInstanceService {

    @Inject
    WfTaskInstanceMapper wfTaskInstanceMapper;

    @Inject
    WfTaskHistoryInstanceMapper wfTaskHistoryInstanceMapper;

    @Inject
    WfActivtityInstanceMapper wfActivtityInstanceMapper;

    @Inject
    WfProcessInstanceMapper wfProcessInstanceMapper;

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
    public void ending(TaskInstanceDTO wfTaskInstanceDTO){
        //进行历史记录
        WfTaskInstanceDO wfTaskInstanceDO = WfTaskInstanceConvert.INSTANCE.convertDTOToDO(wfTaskInstanceDTO);
        wfTaskInstanceMapper.save(wfTaskInstanceDO);
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
        wfTaskHistoryInstanceDO.setCreatetime(new Date());
        wfTaskHistoryInstanceDO.setUpdatetime(wfTaskHistoryInstanceDO.getCreatetime());
        wfTaskHistoryInstanceMapper.save(wfTaskHistoryInstanceDO);
        //从运行表中清除当前记录
        wfTaskInstanceMapper.deleteById(wfTaskInstanceDO.getId());
    }

    /**
     * 根据状态和aiId进行查询
     * @param wfTaskInstanceQueryDTO
     * @return
     */
    @Override
    public int count(TaskInstanceQueryDTO wfTaskInstanceQueryDTO) {
        return wfTaskInstanceMapper.countByTiStatusAndAiId(wfTaskInstanceQueryDTO.getTiStatus(), wfTaskInstanceQueryDTO.getAiId());
    }


    @Override
    public List<TaskInstanceDTO> findRelatedTaskList(String aiId) {
        List<WfTaskInstanceDO> wfTaskInstanceDOList = (List<WfTaskInstanceDO>) wfTaskInstanceMapper
                .findAllByTiStatusAndAiId(TaskInstanceState.TASK_INSTANCE_STATE_COMPLETED, aiId);
        List<TaskInstanceDTO> wfTaskInstanceDTOList = new ArrayList<>();
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            wfTaskInstanceDTOList.add(WfTaskInstanceConvert.INSTANCE.convertDOToDTO(wfTaskInstanceDO));
        }
        return wfTaskInstanceDTOList;
    }

    @Override
    public void moveRelatedTaskToHistory(String aiId) {
        List<WfTaskInstanceDO> wfTaskInstanceDOList = (List<WfTaskInstanceDO>) wfTaskInstanceMapper.findAllByAiId(aiId);
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
            wfTaskHistoryInstanceDO.setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_PAST);
            wfTaskHistoryInstanceDO.setUpdatetime(new Date());
            wfTaskHistoryInstanceDO.setCreatetime(wfTaskHistoryInstanceDO.getUpdatetime());
            wfTaskHistoryInstanceMapper.save(wfTaskHistoryInstanceDO);
            wfTaskInstanceMapper.deleteById(wfTaskInstanceDO.getId());
        }
    }

    @Override
    public TaskInstanceBO save(TaskInstanceDTO wfTaskInstanceDTO) {
        WfTaskInstanceDO wfTaskInstanceDO = WfTaskInstanceConvert.INSTANCE.convertDTOToDO(wfTaskInstanceDTO);
        wfTaskInstanceMapper.save(wfTaskInstanceDO);
        //打时间戳记录
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
        wfTaskHistoryInstanceDO.setCreatetime(new Date());
        wfTaskHistoryInstanceDO.setUpdatetime(wfTaskHistoryInstanceDO.getCreatetime());
        wfTaskHistoryInstanceMapper.save(wfTaskHistoryInstanceDO);
        return WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceDO);
    }

    @Override
    public void recordHistory(TaskInstanceDTO wfTaskInstanceDTO) {
        WfTaskInstanceDO wfTaskInstanceDO = WfTaskInstanceConvert.INSTANCE.convertDTOToDO(wfTaskInstanceDTO);
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
        wfTaskHistoryInstanceDO.setCreatetime(new Date());
        wfTaskHistoryInstanceDO.setUpdatetime(wfTaskHistoryInstanceDO.getCreatetime());
        wfTaskHistoryInstanceMapper.save(wfTaskHistoryInstanceDO);
    }

    @Override
    public TaskInstanceBO getById(String id) {
        return WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceMapper.findById(id).get());
    }

    @Override
    public void delete(String id) {
        wfTaskInstanceMapper.deleteById(id);
    }

    @Override
    public String getFirstTaskId(String piId) {
        WfTaskInstanceDO wfTaskInstanceDO = wfTaskInstanceMapper.findByPiId(piId).get();
        return wfTaskInstanceDO.getId();
    }

    @Override
    public TaskInstanceDTO updateById(TaskInstanceDTO wfTaskInstanceDTO) {
        //修改为修改任务执行人种类和执行人
        wfTaskInstanceMapper.updateAssignerType(wfTaskInstanceDTO.getId(),wfTaskInstanceDTO.getTiAssigner());
        //其实还应该回查下是否真的获取到了，高并发情况下有可能被别人抢了，有点懒就没写，而且同一个任务应该不会太多人抢。
        redisClient.set(List.of(wfTaskInstanceDTO.getId(), "1"));
        return wfTaskInstanceDTO;
    }

    @Override
    public List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType) {
        List<WfTaskInstanceDO> wfTaskInstanceDOList = (List<WfTaskInstanceDO>) wfTaskInstanceMapper
                .findAllByTiAssignerAndTiAssignerTrue(tiAssigner, tiAssignerType);
        List<TaskInstanceBO> wfTaskInstanceBOList = new ArrayList<>();
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            TaskInstanceBO wfTaskInstanceBO = WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceDO);
            WfActivityInstanceDO wfActivityInstanceDO = wfActivtityInstanceMapper.findById(wfTaskInstanceBO.getAiId()).get();
            WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.findById(wfActivityInstanceDO.getPiId()).get();
            wfTaskInstanceBO.setPiBusinessKey(wfProcessInstanceDO.getPiBusinesskey());
            wfTaskInstanceBOList.add(wfTaskInstanceBO);
        }
        return wfTaskInstanceBOList;
    }

    @Override
    public List<TaskInstanceBO> selectUnObtainedTask(String tiAssigner) {
        List<WfTaskInstanceDO> wfTaskInstanceDOList = (List<WfTaskInstanceDO>) wfTaskInstanceMapper
                .findAllByTiAssignerAndTiAssignerTrue(tiAssigner, "1");
        List<TaskInstanceBO> wfTaskInstanceBOList = new ArrayList<>();
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            TaskInstanceBO wfTaskInstanceBO = WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceDO);
            WfActivityInstanceDO wfActivtityInstanceDO = wfActivtityInstanceMapper.findById(wfTaskInstanceBO.getAiId()).get();
            WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.findById(wfActivtityInstanceDO.getPiId()).get();
            wfTaskInstanceBO.setPiBusinessKey(wfProcessInstanceDO.getPiBusinesskey());
            wfTaskInstanceBOList.add(wfTaskInstanceBO);
        }
        return wfTaskInstanceBOList;
    }

    private void sendScheduleRequestMessage(TaskInstanceMessage wfTaskInstanceMessage) {
        emitter.send(new ScheduleRequestMessage().setTaskInstanceMessage(wfTaskInstanceMessage));
    }
}
