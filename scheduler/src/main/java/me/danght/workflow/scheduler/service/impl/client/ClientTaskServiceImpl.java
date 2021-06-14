package me.danght.workflow.scheduler.service.impl.client;

import me.danght.workflow.scheduler.bo.TaskInstanceBO;
import me.danght.workflow.scheduler.dataobject.ParmObject;
import me.danght.workflow.scheduler.dto.BusinessFormDTO;
import me.danght.workflow.scheduler.dto.ProcessParamsRelationDTO;
import me.danght.workflow.scheduler.dto.TaskInstanceDTO;
import me.danght.workflow.scheduler.msg.ScheduleRequestMessage;
import me.danght.workflow.scheduler.msg.TaskInstanceMessage;
import me.danght.workflow.scheduler.service.BusinessFormService;
import me.danght.workflow.scheduler.service.ClientTaskService;
import me.danght.workflow.scheduler.service.ProcessParamsRelationService;
import me.danght.workflow.scheduler.service.TaskInstanceService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ClientTaskServiceImpl implements ClientTaskService {

    @Inject
    ProcessParamsRelationService processParamsRelationService;

    @Inject
    TaskInstanceService taskInstanceService;

    @Inject
    BusinessFormService businessFormService;

    @Inject
    @Channel("schedule-request-out")
    Emitter<ScheduleRequestMessage> emitter;

    @Override
    public boolean completeTask(String tiId, String pdId, String taskNo, Map<String, Object> requiredData) {

        TaskInstanceMessage taskInstanceMessage = new TaskInstanceMessage();
        taskInstanceMessage.setId(tiId);

        Map<String, ParmObject> engineData = new HashMap<>();
        for (Map.Entry<String, Object> entry : requiredData.entrySet()){
            ParmObject parmObject = new ParmObject();
            ProcessParamsRelationDTO processParamsRelationDTO = processParamsRelationService.getEnginePpName(pdId,entry.getKey(),taskNo);
            parmObject.setPpType(processParamsRelationDTO.getPpType());
            parmObject.setVal(entry.getValue());
            engineData.put(processParamsRelationDTO.getEnginePpName(),parmObject);
        }
        taskInstanceMessage.setRequiredData(engineData);
        sendScheduleRequest(taskInstanceMessage);
        return true;
    }

    @Override
    public List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType) {
        List<TaskInstanceBO> taskInstanceBOList =  taskInstanceService.selectUnCompletedTask(tiAssigner,tiAssignerType);
        for(TaskInstanceBO taskInstanceBO : taskInstanceBOList){
            BusinessFormDTO businessFormDTO = businessFormService.selectById(taskInstanceBO.getBfId());
            taskInstanceBO.setBfUrl(businessFormDTO.getFormUrl());
        }
        return taskInstanceBOList;
    }

    @Override
    public TaskInstanceDTO obtainTask(String id, String tiAssigner) {
        TaskInstanceDTO taskInstanceDTO = new TaskInstanceDTO()
                .setId(id)
                .setTiAssigner(tiAssigner)
                .setTiAssignerType("0");
        taskInstanceService.updateById(taskInstanceDTO);
        return taskInstanceDTO;
    }

    @Override
    public List<TaskInstanceBO> selectUnObtainTask(String tiAssigner) {
        List<TaskInstanceBO> taskInstanceBOList =  taskInstanceService.selectUnObtainedTask(tiAssigner);
        for(TaskInstanceBO taskInstanceBO : taskInstanceBOList){
            BusinessFormDTO businessFormDTO = businessFormService.selectById(taskInstanceBO.getBfId());
            taskInstanceBO.setBfUrl(businessFormDTO.getFormUrl());
        }
        return taskInstanceBOList;
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
