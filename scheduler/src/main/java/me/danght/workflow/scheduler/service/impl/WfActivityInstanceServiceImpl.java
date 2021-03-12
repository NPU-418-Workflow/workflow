package me.danght.workflow.scheduler.service.impl;

import com.alibaba.fastjson.JSON;
import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.constant.ActivityInstanceState;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.bo.WfProcessParamsRecordBO;
import me.danght.workflow.scheduler.convert.WfActivityInstanceConvert;
import me.danght.workflow.scheduler.dao.WfActivityHistoryInstanceMapper;
import me.danght.workflow.scheduler.dao.WfActivtityInstanceMapper;
import me.danght.workflow.scheduler.dao.WfProcessParamsRecordMapper;
import me.danght.workflow.scheduler.dataobject.WfActivityInstanceDO;
import me.danght.workflow.scheduler.element.BaseElement;
import me.danght.workflow.scheduler.element.StartEvent;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.service.WfActivtityInstanceService;
import me.danght.workflow.scheduler.service.WfProcessParamsRecordService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-09
 */
@DubboService(interfaceClass = WfActivtityInstanceService.class)
@Singleton
public class WfActivityInstanceServiceImpl implements WfActivtityInstanceService {

    @Inject
    WfActivtityInstanceMapper wfActivtityInstanceMapper;

    @Inject
    WfActivityHistoryInstanceMapper wfActivityHistoryInstanceMapper;

    @Inject
    WfProcessParamsRecordMapper wfProcessParamsRecordMapper;

    @Inject
    WfProcessParamsRecordService wfProcessParamsRecordService;

    @Override
    public ActivityInstanceBO getById(String id) {
        WfActivityInstanceDO wfActivityInstanceDO = wfActivtityInstanceMapper.findById(id).get();
        return WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivityInstanceDO);
    }

    @Override
    public ActivityInstanceBO getByPiIdAndUserTaskNo(String piId, String userTaskNo) {
        List<WfActivityInstanceDO> wfActivityInstanceDOList = (List<WfActivityInstanceDO>) wfActivtityInstanceMapper.findAllByPiIdAndUserTaskNo(piId, userTaskNo);
        return wfActivityInstanceDOList.size() == 0 ? null : WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivityInstanceDOList.get(0));
    }

    @Override
    public void update(ActivityInstanceDTO wfActivityInstanceDTO){
        wfActivtityInstanceMapper.save(WfActivityInstanceConvert.INSTANCE.convertDTOToDO(wfActivityInstanceDTO));
    }

    @Override
    public void clearActivityOfProcess(String piId) {
        wfActivtityInstanceMapper.deleteByPiId(piId);
    }

    //TODO 活动重新开始需要置之前的参数失效
    @Override
    public List<ActivityInstanceBO> addActivityList(List<BaseElement> userTaskList, String piId, String pdId) {
        List<ActivityInstanceBO> wfActivtityInstanceBOList = new ArrayList<>();
        for(BaseElement baseElement : userTaskList){
            UserTask userTask = (UserTask)baseElement;
            List<WfActivityInstanceDO> wfActivityInstanceDOList = (List<WfActivityInstanceDO>) wfActivtityInstanceMapper.findAllByPiIdAndUserTaskNo(piId, userTask.getNo());
            WfActivityInstanceDO wfActivityInstanceDO = wfActivityInstanceDOList.get(0);
            List<String> assList = new ArrayList<>();
            if(userTask.getAssignees() != null && userTask.getAssignees().size() > 0) {
                assList.addAll(userTask.getAssignees());
            }
            //即席判断与处理
            if(userTask.getDynamicAssignees() != null && userTask.getDynamicAssignees().length() > 0){
                String[] dynamicAssigneesParam = userTask.getDynamicAssignees().split(",");
                WfProcessParamsRecordBO dynamicAssigneesPP = wfProcessParamsRecordService.getByEnginePpName(dynamicAssigneesParam[3],piId,pdId,dynamicAssigneesParam[0]);
                String[] dynamicAssignees = dynamicAssigneesPP.getPpRecordValue().split(",");
                for(String member : dynamicAssignees){
                    assList.add(member);
                }
            }
            //即席内容处理结束
            if(wfActivityInstanceDO != null){
                wfActivityInstanceDO.setAiAssignerId(JSON.toJSONString(assList));
                wfActivityInstanceDO.setActiveTiNum(assList.size());
                wfActivityInstanceDO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING);
                wfActivityInstanceDO.setUpdatetime(new Date());
                wfActivtityInstanceMapper.save(wfActivityInstanceDO);
                wfProcessParamsRecordMapper.deleteByAiId(wfActivityInstanceDO.getId());
                wfActivtityInstanceBOList.add(WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivityInstanceDO));
                //状态转为运行，打时间戳
                wfActivityHistoryInstanceMapper.save(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivityInstanceDO));
            }else {
                wfActivityInstanceDO = new WfActivityInstanceDO()
                        .setAiName(userTask.getName())
                        .setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING)
                        .setAiAssignerId(JSON.toJSONString(assList))
                        .setAiAssignerType(userTask.getAssigneeType())
                        .setBfId(userTask.getPageKey())
                        .setUserTaskNo(userTask.getNo())
                        .setAiCategory(userTask.getTaskType())
                        .setPiId(piId)
                        .setPdId(pdId)
                        .setActiveTiNum(assList.size());
                //这块做个补丁，但其实是流程图设计的不好先这么弄下
                if(wfActivityInstanceDO.getAiAssignerType() == null){
                    wfActivityInstanceDO.setAiAssignerType(userTask.getDynamicAssigneeType());
                }
                wfActivityInstanceDO.setCreatetime(new Date());
                wfActivityInstanceDO.setUpdatetime(wfActivityInstanceDO.getCreatetime());
                wfActivtityInstanceMapper.save(wfActivityInstanceDO);
                wfActivtityInstanceBOList.add(WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivityInstanceDO));
                //1025新增，之前活动开启时忘了打时间戳
                wfActivityHistoryInstanceMapper.save(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivityInstanceDO));
            }
        }
        return wfActivtityInstanceBOList;
    }

    @Override
    public ActivityInstanceBO addStartEventActivity(StartEvent startEvent, String piId, String pdId, String piStarter) {
        List<WfActivityInstanceDO> wfActivityInstanceDOList = (List<WfActivityInstanceDO>) wfActivtityInstanceMapper.findAllByPiIdAndUserTaskNo(piId, startEvent.getNo());
        WfActivityInstanceDO wfActivityInstanceDO = wfActivityInstanceDOList.get(0);
        if(wfActivityInstanceDO != null){
            wfActivityInstanceDO.setAiAssignerId(JSON.toJSONString(piStarter));
            wfActivityInstanceDO.setActiveTiNum(1);
            wfActivityInstanceDO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING);
            wfActivityInstanceDO.setUpdatetime(new Date());
            wfActivtityInstanceMapper.save(wfActivityInstanceDO);
            wfProcessParamsRecordMapper.deleteByAiId(wfActivityInstanceDO.getId());
            wfActivityHistoryInstanceMapper.save(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivityInstanceDO));
        }else{
            wfActivityInstanceDO = new WfActivityInstanceDO()
                    .setAiName(startEvent.getName())
                    .setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING)
                    .setAiAssignerId(JSON.toJSONString(piStarter))
                    .setAiAssignerType("0")
                    .setUserTaskNo(startEvent.getNo())
                    .setAiCategory("01")
                    .setPiId(piId)
                    .setPdId(pdId)
                    .setActiveTiNum(1);
            wfActivityInstanceDO.setCreatetime(new Date());
            wfActivityInstanceDO.setUpdatetime(wfActivityInstanceDO.getCreatetime());
            wfActivtityInstanceMapper.save(wfActivityInstanceDO);
            wfActivityHistoryInstanceMapper.save(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivityInstanceDO));
        }
        return WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivityInstanceDO);
    }
}
