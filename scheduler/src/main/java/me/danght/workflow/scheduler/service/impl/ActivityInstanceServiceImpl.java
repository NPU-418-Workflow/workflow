package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.bo.ActivityInstanceBO;
import me.danght.workflow.scheduler.bo.ProcessParamsRecordBO;
import me.danght.workflow.scheduler.constant.ActivityInstanceState;
import me.danght.workflow.scheduler.convert.ActivityInstanceConvert;
import me.danght.workflow.scheduler.dao.ActivityHistoryInstanceRepository;
import me.danght.workflow.scheduler.dao.ActivityInstanceRepository;
import me.danght.workflow.scheduler.dao.ProcessParamsRecordRepository;
import me.danght.workflow.scheduler.dataobject.ActivityInstanceDO;
import me.danght.workflow.scheduler.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.element.BaseElement;
import me.danght.workflow.scheduler.element.StartEvent;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import java.util.*;

/**
 *  活动实例服务
 *
 * @author wenxiang
 * @author DangHT
 * @since 2019-10-09
 */
@ApplicationScoped
@ActivateRequestContext
public class ActivityInstanceServiceImpl implements ActivityInstanceService {

    @Inject
    ActivityInstanceRepository activityInstanceRepository;

    @Inject
    ActivityHistoryInstanceRepository activityHistoryInstanceRepository;

    @Inject
    ProcessParamsRecordRepository processParamsRecordRepository;

    @Inject
    ProcessParamsRecordService processParamsRecordService;

    @Override
    public ActivityInstanceBO getById(String id) {
        ActivityInstanceDO activityInstanceDO = activityInstanceRepository.findById(id).orElse(null);
        return ActivityInstanceConvert.INSTANCE.convertDOToBO(activityInstanceDO);
    }

    @Override
    public ActivityInstanceBO getByPiIdAndUserTaskNo(String piId, String userTaskNo) {
        List<ActivityInstanceDO> activityInstanceDOList = activityInstanceRepository.findAllByPiIdAndUserTaskNo(piId, userTaskNo);
        return activityInstanceDOList.size() == 0 ? null : ActivityInstanceConvert.INSTANCE.convertDOToBO(activityInstanceDOList.get(0));
    }

    @Override
    public void update(ActivityInstanceDTO activityInstanceDTO){
        activityInstanceRepository.save(ActivityInstanceConvert.INSTANCE.convertDTOToDO(activityInstanceDTO));
    }

    @Override
    public void clearActivityOfProcess(String piId) {
        activityInstanceRepository.deleteByPiId(piId);
    }

    //TODO 活动重新开始需要置之前的参数失效
    @Override
    public List<ActivityInstanceBO> addActivityList(List<BaseElement> userTaskList, String piId, String pdId) {
        List<ActivityInstanceBO> activityInstanceBOList = new ArrayList<>();
        for(BaseElement baseElement : userTaskList){
            UserTask userTask = (UserTask)baseElement;
            List<ActivityInstanceDO> activityInstanceDOList = activityInstanceRepository.findAllByPiIdAndUserTaskNo(piId, userTask.getNo());
            ActivityInstanceDO activityInstanceDO;
            List<String> assList = new ArrayList<>();
            if(userTask.getAssignees() != null && userTask.getAssignees().size() > 0) {
                assList.addAll(userTask.getAssignees());
            }
            //即席判断与处理
            if(userTask.getDynamicAssignees() != null && userTask.getDynamicAssignees().length() > 0){
                String[] dynamicAssigneesParam = userTask.getDynamicAssignees().split(",");
                ProcessParamsRecordBO dynamicAssigneesPP = processParamsRecordService.getByEnginePpName(dynamicAssigneesParam[3],piId,pdId,dynamicAssigneesParam[0]);
                String[] dynamicAssignees = dynamicAssigneesPP.getPpRecordValue().split(",");
                Collections.addAll(assList, dynamicAssignees);
            }
            //即席内容处理结束
            if(activityInstanceDOList.size() > 0){
                activityInstanceDO = activityInstanceDOList.get(0);
                activityInstanceDO.setAiAssignerId(assList.toString());
                activityInstanceDO.setActiveTiNum(assList.size());
                activityInstanceDO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING);
                activityInstanceDO.setUpdateTime(new Date());
                activityInstanceRepository.save(activityInstanceDO);
                processParamsRecordRepository.deleteByAiId(activityInstanceDO.getId());
                activityInstanceBOList.add(ActivityInstanceConvert.INSTANCE.convertDOToBO(activityInstanceDO));
                //状态转为运行，打时间戳
                activityHistoryInstanceRepository.save(ActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(activityInstanceDO));
            }else {
                activityInstanceDO = new ActivityInstanceDO()
                        .setAiName(userTask.getName())
                        .setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING)
                        .setAiAssignerId(assList.toString())
                        .setAiAssignerType(userTask.getAssigneeType())
                        .setBfId(userTask.getPageKey())
                        .setUserTaskNo(userTask.getNo())
                        .setAiCategory(userTask.getTaskType())
                        .setPiId(piId)
                        .setPdId(pdId)
                        .setActiveTiNum(assList.size());
                //这块做个补丁，但其实是流程图设计的不好先这么弄下
                if(activityInstanceDO.getAiAssignerType() == null){
                    activityInstanceDO.setAiAssignerType(userTask.getDynamicAssigneeType());
                }
                activityInstanceDO.setCreateTime(new Date());
                activityInstanceDO.setUpdateTime(activityInstanceDO.getCreateTime());
                activityInstanceRepository.save(activityInstanceDO);
                activityInstanceBOList.add(ActivityInstanceConvert.INSTANCE.convertDOToBO(activityInstanceDO));
                //1025新增，之前活动开启时忘了打时间戳
                activityHistoryInstanceRepository.save(ActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(activityInstanceDO));
            }
        }
        return activityInstanceBOList;
    }

    @Override
    public ActivityInstanceBO addStartEventActivity(StartEvent startEvent, String piId, String pdId, String piStarter) {
        List<ActivityInstanceDO> activityInstanceDOList = activityInstanceRepository.findAllByPiIdAndUserTaskNo(piId, startEvent.getNo());
        ActivityInstanceDO activityInstanceDO;
        if(activityInstanceDOList.size() > 0){
            activityInstanceDO = activityInstanceDOList.get(0);
            activityInstanceDO.setAiAssignerId(piStarter);
            activityInstanceDO.setActiveTiNum(1);
            activityInstanceDO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING);
            activityInstanceDO.setUpdateTime(new Date());
            activityInstanceRepository.save(activityInstanceDO);
            processParamsRecordRepository.deleteByAiId(activityInstanceDO.getId());
            activityHistoryInstanceRepository.save(ActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(activityInstanceDO));
        }else{
            activityInstanceDO = new ActivityInstanceDO()
                    .setAiName(startEvent.getName())
                    .setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING)
                    .setAiAssignerId(piStarter)
                    .setAiAssignerType("0")
                    .setUserTaskNo(startEvent.getNo())
                    .setAiCategory("01")
                    .setPiId(piId)
                    .setPdId(pdId)
                    .setActiveTiNum(1);
            activityInstanceDO.setCreateTime(new Date());
            activityInstanceDO.setUpdateTime(activityInstanceDO.getCreateTime());
            activityInstanceRepository.save(activityInstanceDO);
            activityHistoryInstanceRepository.save(ActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(activityInstanceDO));
        }
        return ActivityInstanceConvert.INSTANCE.convertDOToBO(activityInstanceDO);
    }
}
