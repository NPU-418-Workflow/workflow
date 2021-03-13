package me.danght.workflow.scheduler.mq;

import io.quarkus.redis.client.RedisClient;
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
import me.danght.workflow.scheduler.dao.redis.BpmnModelCacheDao;
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
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 调度器调度请求消费者
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

    //@Inject
    //SqlSessionTemplate sqlsession;

    @Inject
    BpmnModelCacheDao bpmnModelCacheDao;

    @Incoming("schedule-request-in")
    public void receive(ScheduleRequestMessage scheduleRequestMessage) {
        //合并并发活动数据以及判断是否存在关联活动未完成的情况
        //注：通过当前userTask的下一个元素的入度并结合当前处于正在执行状态的活动判断是否都已完成
        if(scheduleRequestMessage.getProcessInstanceMessage() != null){
            /*//幂等性保证*/
            ProcessInstanceBO processInstanceBO = processInstanceService
                    .startProcess(
                            ProcessInstanceConvert.INSTANCE.convertMessageToStartDTO(
                                    scheduleRequestMessage.getProcessInstanceMessage()
                            )
                    );
            ProcessDefinitionBO processDefinitionBO = processDefinitionService
                    .getWfProcessDefinitionById(
                            processInstanceBO.getPdId()
                    );
            BpmnModel bpmnModel = bpmnModelCacheDao.get(processDefinitionBO.getId());
            if (bpmnModel == null) {
                bpmnModel = BpmnXMLConvertUtil.ConvertToBpmnModel(processDefinitionBO.getPtContent());
            }
            //新版从这开始，新建一个token
            Token rootToken = new Token();
            rootToken.setPiId(processInstanceBO.getId());
            rootToken.setPdId(processInstanceBO.getPdId());
            //no在start任务里面配
            rootToken.setParentId("0");
            rootToken.setElementNo(bpmnModel.getProcess().getStartEvent().getNo());
            bpmnModel.getProcess().getStartEvent().execute(rootToken);
            //自动完成第一个任务
            TaskInstanceMessage wfTaskInstanceMessage = new TaskInstanceMessage()
                    .setId(taskInstanceService.getFirstTaskId(processInstanceBO.getId()))
                    .setRequiredData(scheduleRequestMessage.getProcessInstanceMessage().getRequiredData());
            scheduleRequestMessage.setTaskInstanceMessage(wfTaskInstanceMessage);
            taskSchedule(scheduleRequestMessage);
        }else if(scheduleRequestMessage.getTaskInstanceMessage() != null) {
            //幂等性保证
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
        //从运行表中清除当前记录
        taskInstanceService.delete(taskInstanceBO.getId());
        //根据接收到的WfTaskInstanceMessage判断接下来的流转情况
        //记录任务带的流程参数数据(任务级)
        ActivityInstanceBO activityInstanceBO = activityInstanceService.getById(taskInstanceBO.getAiId());
        processParamsRecordService.recordRequiredData(
                activityInstanceBO.getId(),
                scheduleRequestMessage.getTaskInstanceMessage().getId(),
                scheduleRequestMessage.getTaskInstanceMessage().getRequiredData()
        );
        //sqlsession.clearCache();
        //先判断是否有会签关联任务未完成，查看activeTiNum数目，减1并更新，若减1后不为0，则等待
        activityInstanceBO.setActiveTiNum(activityInstanceBO.getActiveTiNum() - 1);
        ActivityInstanceDTO activityInstanceDTO = ActivityInstanceConvert.INSTANCE.convertBOToDTO(activityInstanceBO);
        activityInstanceService.update(activityInstanceDTO);
        if (activityInstanceDTO.getActiveTiNum() > 0)
            return;
        activityInstanceDTO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_COMPLETED);
        activityInstanceService.update(activityInstanceDTO);
        //活动历史库打时间戳记录
        scheduleManageService.recordActivityHistory(activityInstanceDTO);
        //之后开始找接下来的活动，返回值为空代表当前还有关联活动未完成，返回值为endevent说明要结束流程
        ProcessDefinitionBO processDefinitionBO = processDefinitionService.getWfProcessDefinitionById(activityInstanceBO.getPdId());
        BpmnModel bpmnModel = bpmnModelCacheDao.get(processDefinitionBO.getId());
        if (bpmnModel == null) {
            bpmnModel = BpmnXMLConvertUtil.ConvertToBpmnModel(processDefinitionBO.getPtContent());
        }
        //Token复原，关键在子父关系一整套都要复原
        Token cToken = tokenService.getCurrentToken(activityInstanceBO.getPiId(), activityInstanceBO.getUserTaskNo(), bpmnModel.getProcess());
        cToken.signal();
    }

}