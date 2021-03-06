package me.danght.workflow.scheduler.mq;

import io.quarkus.redis.client.RedisClient;
import io.smallrye.reactive.messaging.annotations.Blocking;
import me.danght.workflow.common.api.schduler.ProcessDefinitionService;
import me.danght.workflow.common.api.schduler.ProcessInstanceService;
import me.danght.workflow.common.api.schduler.TaskHistoryInstanceService;
import me.danght.workflow.common.api.schduler.TaskInstanceService;
import me.danght.workflow.common.bo.*;
import me.danght.workflow.common.constant.ActivityInstanceState;
import me.danght.workflow.common.constant.TaskInstanceState;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.common.msg.ScheduleRequestMessage;
import me.danght.workflow.common.msg.TaskInstanceMessage;
import me.danght.workflow.scheduler.convert.ActivityInstanceConvert;
import me.danght.workflow.scheduler.convert.ProcessInstanceConvert;
import me.danght.workflow.scheduler.convert.TaskHistoryInstanceConvert;
import me.danght.workflow.scheduler.convert.TaskInstanceConvert;
import me.danght.workflow.scheduler.dao.ProcessInstanceRepository;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.dao.redis.BpmnModelCacheDao;
import me.danght.workflow.scheduler.dataobject.ProcessInstanceDO;
import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.element.BpmnModel;
import me.danght.workflow.scheduler.service.ScheduleManageService;
import me.danght.workflow.scheduler.service.TokenService;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;
import me.danght.workflow.scheduler.tools.BpmnXMLConvertUtil;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * ??????????????????????????????
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-26
 */
@ApplicationScoped
public class ScheduleRequestConsumer {
    @Inject
    ScheduleManageService scheduleManageService;

    @Inject
    ProcessDefinitionService processDefinitionService;

    @Inject
    ProcessParamsRecordService processParamsRecordService;

    @Inject
    ActivityInstanceService activityInstanceService;

    @Inject
    TokenService tokenService;

    @Inject
    TaskInstanceService taskInstanceService;

    @Inject
    TaskHistoryInstanceService taskHistoryInstanceService;

    @Inject
    ProcessInstanceService processInstanceService;

    @Inject
    RedisClient redisClient;

    @Inject
    TaskInstanceRepository taskInstanceRepository;

    @Inject
    TokenRepository tokenRepository;

    @Inject
    ProcessInstanceRepository processInstanceRepository;

    //@Inject
    //SqlSessionTemplate sqlsession;

    @Inject
    BpmnModelCacheDao bpmnModelCacheDao;

    @Incoming("schedule-request-in")
    @Blocking
    @Transactional
    public void receive(ScheduleRequestMessage scheduleRequestMessage) {
        //??????????????????????????????????????????????????????????????????????????????
        //??????????????????userTask???????????????????????????????????????????????????????????????????????????????????????????????????
        if(scheduleRequestMessage.getProcessInstanceMessage() != null){
            /*//???????????????*/
            ProcessInstanceBO processInstanceBO = processInstanceService
                    .startProcess(
                            ProcessInstanceConvert.INSTANCE.convertMessageToStartDTO(
                                    scheduleRequestMessage.getProcessInstanceMessage()
                            )
                    );
            ProcessDefinitionBO processDefinitionBO = processDefinitionService
                    .getProcessDefinitionById(
                            processInstanceBO.getPdId()
                    );
            BpmnModel bpmnModel = bpmnModelCacheDao.get(processDefinitionBO.getId());
            if (bpmnModel == null) {
                bpmnModel = BpmnXMLConvertUtil.ConvertToBpmnModel(processDefinitionBO.getPtContent());
            }
            //?????????????????????????????????token
            Token rootToken = new Token();
            rootToken.setPiId(processInstanceBO.getId());
            rootToken.setPdId(processInstanceBO.getPdId());
            //no???start???????????????
            rootToken.setParentId("0");
            rootToken.setElementNo(bpmnModel.getProcess().getStartEvent().getNo());
            //????????????,???????????????????????????usertask????????????token????????????
            rootToken.setCreateTime(new Date());
            //????????????????????????????????????????????????????????????????????????fork??????????????????parentId?????????????????????Id
            tokenRepository.save(rootToken);
            ProcessInstanceDO processInstanceDO =  processInstanceRepository.findById(rootToken.getPiId()).orElse(null);
            if (processInstanceDO != null) {
                ActivityInstanceBO activityInstanceBO = activityInstanceService
                        .addStartEventActivity(
                                bpmnModel.getProcess().getStartEvent(),
                                rootToken.getPiId(),
                                rootToken.getPdId(),
                                processInstanceDO.getPiStarter()
                        );
                pushTask(activityInstanceBO, processInstanceDO.getPiStarter());
            }
            //???????????????????????????
            TaskInstanceMessage taskInstanceMessage = new TaskInstanceMessage()
                    .setId(taskInstanceService.getFirstTaskId(processInstanceBO.getId()))
                    .setRequiredData(scheduleRequestMessage.getProcessInstanceMessage().getRequiredData());
            scheduleRequestMessage.setTaskInstanceMessage(taskInstanceMessage);
            taskSchedule(scheduleRequestMessage);
        }else if(scheduleRequestMessage.getTaskInstanceMessage() != null) {
            //???????????????
            if (!redisClient.del(List.of(scheduleRequestMessage.getTaskInstanceMessage().getId())).toBoolean()) {
                return;
            }
            try {
                taskSchedule(scheduleRequestMessage);
            }catch (Exception e){
                redisClient.set(List.of(scheduleRequestMessage.getTaskInstanceMessage().getId(),"1"));
                System.out.println(redisClient.get(scheduleRequestMessage.getTaskInstanceMessage().getId()));
                throw e;
            }
        }
    }

    void taskSchedule(ScheduleRequestMessage scheduleRequestMessage){
        TaskInstanceBO taskInstanceBO = taskInstanceService
                .getById(scheduleRequestMessage.getTaskInstanceMessage().getId());
        taskInstanceBO.setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_COMPLETED);
        TaskHistoryInstanceBO taskHistoryInstanceBO = TaskInstanceConvert.INSTANCE.convertBOToHistory(taskInstanceBO);
        taskHistoryInstanceBO.setCreateTime(new Date());
        taskHistoryInstanceBO.setUpdateTime(taskHistoryInstanceBO.getCreateTime());
        taskHistoryInstanceService.save(TaskHistoryInstanceConvert.INSTANCE.convertBOToDTO(taskHistoryInstanceBO));
        //?????????????????????????????????
        taskInstanceService.delete(taskInstanceBO.getId());
        //??????????????????TaskInstanceMessage??????????????????????????????
        //????????????????????????????????????(?????????)
        ActivityInstanceBO activityInstanceBO = activityInstanceService.getById(taskInstanceBO.getAiId());
        processParamsRecordService.recordRequiredData(
                activityInstanceBO.getId(),
                scheduleRequestMessage.getTaskInstanceMessage().getId(),
                scheduleRequestMessage.getTaskInstanceMessage().getRequiredData()
        );
        //sqlsession.clearCache();
        //??????????????????????????????????????????????????????activeTiNum????????????1??????????????????1?????????0????????????
        activityInstanceBO.setActiveTiNum(activityInstanceBO.getActiveTiNum() - 1);
        ActivityInstanceDTO activityInstanceDTO = ActivityInstanceConvert.INSTANCE.convertBOToDTO(activityInstanceBO);
        activityInstanceService.update(activityInstanceDTO);
        if (activityInstanceDTO.getActiveTiNum() > 0)
            return;
        activityInstanceDTO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_COMPLETED);
        activityInstanceService.update(activityInstanceDTO);
        //?????????????????????????????????
        scheduleManageService.recordActivityHistory(activityInstanceDTO);
        //?????????????????????????????????????????????????????????????????????????????????????????????????????????endevent?????????????????????
        ProcessDefinitionBO processDefinitionBO = processDefinitionService.getProcessDefinitionById(activityInstanceBO.getPdId());
        BpmnModel bpmnModel = bpmnModelCacheDao.get(processDefinitionBO.getId());
        if (bpmnModel == null) {
            bpmnModel = BpmnXMLConvertUtil.ConvertToBpmnModel(processDefinitionBO.getPtContent());
        }
        //Token???????????????????????????????????????????????????
        Token cToken = tokenService.getCurrentToken(activityInstanceBO.getPiId(), activityInstanceBO.getUserTaskNo(), bpmnModel.getProcess());
        cToken.signal(processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
    }

    public TaskInstanceDO pushTask(ActivityInstanceBO activityInstanceBO, String ass){
        TaskInstanceDO taskInstanceDO = new TaskInstanceDO()
                .setTiName(activityInstanceBO.getAiName())
                .setTiAssigner(ass)
                .setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_RUNNING)
                .setBfId(activityInstanceBO.getBfId())
                .setAiId(activityInstanceBO.getId())
                .setPdId(activityInstanceBO.getPdId())
                .setTiAssignerType(activityInstanceBO.getAiAssignerType())
                .setPiId(activityInstanceBO.getPiId())
                .setUserTaskNo(activityInstanceBO.getUserTaskNo());
        taskInstanceDO.setCreateTime(new Date());
        taskInstanceDO.setUpdateTime(taskInstanceDO.getCreateTime());
        taskInstanceRepository.save(taskInstanceDO);
        redisClient.append(taskInstanceDO.getId(), "1");
        return taskInstanceDO;
    }

}