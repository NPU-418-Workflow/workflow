package me.danght.workflow.service.impl.client;

import me.danght.workflow.common.api.form.ClientTaskService;
import me.danght.workflow.common.api.form.ProcessParamsRelationService;
import me.danght.workflow.common.api.schduler.TaskInstanceService;
import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.dataobject.ParmObject;
import me.danght.workflow.common.dto.ProcessParamsRelationDTO;
import me.danght.workflow.common.dto.TaskInstanceDTO;
import me.danght.workflow.common.msg.ScheduleRequestMessage;
import me.danght.workflow.common.msg.TaskInstanceMessage;
import me.danght.workflow.dto.BusinessFormDTO;
import me.danght.workflow.service.BusinessFormService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService(interfaceClass = ClientTaskService.class)
@Singleton
public class ClientTaskServiceImpl implements ClientTaskService {

    @Inject
    ProcessParamsRelationService wfProcessParamsRelationService;

    @DubboReference
    TaskInstanceService wfTaskInstanceService;

    @Inject
    BusinessFormService businessFormService;

    @Inject
    @Channel("schedule-request-out")
    Emitter<ScheduleRequestMessage> emitter;

    @Override
    public boolean completeTask(String tiId, String pdId, String taskNo, Map<String, Object> requiredData) {

        TaskInstanceMessage wfTaskInstanceMessage = new TaskInstanceMessage();
        wfTaskInstanceMessage.setId(tiId);

        Map<String, ParmObject> engineData = new HashMap<>();
        for (Map.Entry<String, Object> entry : requiredData.entrySet()){
            ParmObject parmObject = new ParmObject();
            ProcessParamsRelationDTO wfProcessParamsRelationDTO = wfProcessParamsRelationService.getEnginePpName(pdId,entry.getKey(),taskNo);
            parmObject.setPpType(wfProcessParamsRelationDTO.getPpType());
            parmObject.setVal(entry.getValue());
            engineData.put(wfProcessParamsRelationDTO.getEnginePpName(),parmObject);
        }
        wfTaskInstanceMessage.setRequiredData(engineData);
        sendScheduleRequest(wfTaskInstanceMessage);
        return true;
    }

    @Override
    public List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType) {
        List<TaskInstanceBO> wfTaskInstanceBOList =  wfTaskInstanceService.selectUnCompletedTask(tiAssigner,tiAssignerType);
        for(TaskInstanceBO wfTaskInstanceBO : wfTaskInstanceBOList){
            BusinessFormDTO businessFormDTO = businessFormService.selectById(wfTaskInstanceBO.getBfId());
            wfTaskInstanceBO.setBfUrl(businessFormDTO.getFormUrl());
        }
        return wfTaskInstanceBOList;
    }

    @Override
    public TaskInstanceDTO obtainTask(String id, String tiAssigner) {
        TaskInstanceDTO wfTaskInstanceDTO = new TaskInstanceDTO()
                .setId(id)
                .setTiAssigner(tiAssigner)
                .setTiAssignerType("0");
        wfTaskInstanceService.updateById(wfTaskInstanceDTO);
        return wfTaskInstanceDTO;
    }

    @Override
    public List<TaskInstanceBO> selectUnObtainTask(String tiAssigner) {
        List<TaskInstanceBO> wfTaskInstanceBOList =  wfTaskInstanceService.selectUnObtainedTask(tiAssigner);
        for(TaskInstanceBO wfTaskInstanceBO : wfTaskInstanceBOList){
            BusinessFormDTO businessFormDTO = businessFormService.selectById(wfTaskInstanceBO.getBfId());
            wfTaskInstanceBO.setBfUrl(businessFormDTO.getFormUrl());
        }
        return wfTaskInstanceBOList;
    }

    public void sendScheduleRequest(TaskInstanceMessage taskInstanceMessage) {
        emitter.send(new ScheduleRequestMessage().setTaskInstanceMessage(taskInstanceMessage));
    }

    //public TransactionSendResult sendMessageInTransaction(TaskInstanceMessage taskInstanceMessage) {
    //    // 创建 Demo07Message 消息
    //    Message<ScheduleRequestMessage> message = MessageBuilder.withPayload(new ScheduleRequestMessage().setTaskInstanceMessage(taskInstanceMessage))
    //            .build();
    //    // 发送事务消息,最后一个参数事务处理用
    //    return rocketMQTemplate.sendMessageInTransaction("form-transaction-producer-group", ScheduleRequestMessage.TOPIC, message, null);
    //}

}
