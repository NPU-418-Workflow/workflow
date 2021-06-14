package me.danght.workflow.scheduler.service.impl.client;

import me.danght.workflow.scheduler.bo.ProcessDefinitionBO;
import me.danght.workflow.scheduler.bo.ProcessInstanceBO;
import me.danght.workflow.scheduler.dataobject.ParmObject;
import me.danght.workflow.scheduler.dto.BusinessFormDTO;
import me.danght.workflow.scheduler.dto.ProcessParamsRelationDTO;
import me.danght.workflow.scheduler.msg.ProcessInstanceMessage;
import me.danght.workflow.scheduler.msg.ScheduleRequestMessage;
import me.danght.workflow.scheduler.service.*;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ClientProcessServiceImpl implements ClientProcessService {

    @Inject
    ProcessDefinitionService processDefinitionService;

    @Inject
    ProcessInstanceService processInstanceService;

    @Inject
    BusinessFormService businessFormService;

    @Inject
    ProcessParamsRelationService processParamsRelationService;

    @Inject
    @Channel("schedule-request-out")
    Emitter<ScheduleRequestMessage> emitter;

    @Override
    public boolean startProcess(String pdId, String piName, String piStarter, String piBusinessKey, Map<String, ParmObject> requiredData) {
        ProcessInstanceMessage processInstanceMessage = new ProcessInstanceMessage();
        Map<String, ParmObject> engineData = new HashMap<>();
        for (Map.Entry<String, ParmObject> entry : requiredData.entrySet()){
            ParmObject parmObject = new ParmObject();
            ProcessParamsRelationDTO processParamsRelationDTO = processParamsRelationService.getEnginePpName(pdId,entry.getKey(),"startevent1");
            parmObject.setPpType(processParamsRelationDTO.getPpType());
            parmObject.setVal(entry.getValue().getVal());
            engineData.put(processParamsRelationDTO.getEnginePpName(),parmObject);
        }
        processInstanceMessage.setPdId(pdId)
                .setPiBusinessKey(piBusinessKey)
                .setPiName(piName)
                .setPiStarter(piStarter)
                .setRequiredData(engineData);
        sendScheduleRequest(processInstanceMessage);
        return true;
    }

    @Override
    public List<ProcessDefinitionBO> queryDefinitionList() {
        List<ProcessDefinitionBO> processDefinitionBOList = processDefinitionService.queryDefinitionList();
        for(ProcessDefinitionBO processDefinitionBO : processDefinitionBOList){
            BusinessFormDTO businessFormDTO = businessFormService.selectById(processDefinitionBO.getStartForm());
            processDefinitionBO.setStartForm(businessFormDTO.getFormUrl());
        }
        return processDefinitionBOList;
    }

    @Override
    public void changeProcessState(String piId, String state) {
        processInstanceService.changeProcessState(piId,state);
    }

    @Override
    public List<ProcessInstanceBO> getProcessListByUserId(String piStarter) {
        return processInstanceService.getProcessListByUserId(piStarter);
    }

    public void sendScheduleRequest(ProcessInstanceMessage processInstanceMessage) {
        emitter.send(new ScheduleRequestMessage().setProcessInstanceMessage(processInstanceMessage));
    }

    //public TransactionSendResult sendMessageInTransaction(ProcessInstanceMessage wfProcessInstanceMessage) {
    //    // 创建 Demo07Message 消息
    //    Message<ScheduleRequestMessage> message = MessageBuilder.withPayload(new ScheduleRequestMessage().setProcessInstanceMessage(wfProcessInstanceMessage))
    //            .build();
    //    // 发送事务消息,最后一个参数事务处理用
    //    return rocketMQTemplate.sendMessageInTransaction("form-transaction-producer-group", ScheduleRequestMessage.TOPIC, message, null);
    //}


}
