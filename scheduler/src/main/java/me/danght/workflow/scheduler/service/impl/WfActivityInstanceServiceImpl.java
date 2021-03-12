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
    public ActivityInstanceBO getOneByMap(Map<String, Object> conditionMap) {
        List<WfActivityInstanceDO> wfActivityInstanceDOList = wfActivtityInstanceMapper.selectByMap(conditionMap);
        if (wfActivityInstanceDOList.size() == 0) {
            return null;
        }
        return WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivtityInstanceDOList.get(0));
    }

    @Override
    public void update(ActivityInstanceDTO wfActivtityInstanceDTO){
        wfActivtityInstanceMapper.updateById(WfActivityInstanceConvert.INSTANCE.convertDTOToDO(wfActivtityInstanceDTO));
    }

    @Override
    public void clearActivityOfProcess(String piId) {
        QueryWrapper<WfActivityInstanceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("pi_id",piId);
        wfActivtityInstanceMapper.delete(queryWrapper);
    }

    //TODO 活动重新开始需要置之前的参数失效
    @Override
    public List<ActivityInstanceBO> addActivityList(List<BaseElement> userTaskList, String piId, String pdId) {
        List<ActivityInstanceBO> wfActivtityInstanceBOList = new ArrayList<>();
        for(BaseElement baseElement : userTaskList){
            UserTask userTask = (UserTask)baseElement;
            QueryWrapper<WfActivityInstanceDO> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("pi_id",piId)
                    .eq("usertask_no",userTask.getNo());
            WfActivityInstanceDO wfActivtityInstanceDO = wfActivtityInstanceMapper.selectOne(queryWrapper);
            List<String> assList = new ArrayList<>();//即席和固定执行人的合集
            if(userTask.getAssignees() != null && userTask.getAssignees().size() > 0)
                assList.addAll(userTask.getAssignees());
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
            if(wfActivtityInstanceDO != null){
                wfActivtityInstanceDO.setAiAssignerId(JSON.toJSONString(assList));
                wfActivtityInstanceDO.setActiveTiNum(assList.size());
                wfActivtityInstanceDO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING);
                wfActivtityInstanceDO.setUpdatetime(new Date());
                wfActivtityInstanceMapper.updateById(wfActivtityInstanceDO);
                //若之前活动产生了参数，则删除
                Map<String,Object> conditionMap = new HashMap<>();
                conditionMap.put("ai_id",wfActivtityInstanceDO.getId());
                wfProcessParamsRecordMapper.deleteByMap(conditionMap);
                wfActivtityInstanceBOList.add(WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivtityInstanceDO));
                //状态转为运行，打时间戳
                wfActivityHistoryInstanceMapper.insert(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivtityInstanceDO));
            }else {
                wfActivtityInstanceDO = new WfActivityInstanceDO()
                        .setAiName(userTask.getName())
                        .setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING)
                        .setAiAssignerId(JSON.toJSONString(assList))
                        .setAiAssignerType(userTask.getAssigneeType())
                        .setBfId(userTask.getPageKey())
                        .setUsertaskNo(userTask.getNo())
                        .setAiCategory(userTask.getTaskType())
                        .setPiId(piId)
                        .setPdId(pdId)
                        .setActiveTiNum(assList.size());
                //这块做个补丁，但其实是流程图设计的不好先这么弄下
                if(wfActivtityInstanceDO.getAiAssignerType() == null){
                    wfActivtityInstanceDO.setAiAssignerType(userTask.getDynamicAssigneeType());
                }
                wfActivtityInstanceDO.setCreatetime(new Date());
                wfActivtityInstanceDO.setUpdatetime(wfActivtityInstanceDO.getCreatetime());
                wfActivtityInstanceMapper.insert(wfActivtityInstanceDO);
                wfActivtityInstanceBOList.add(WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivtityInstanceDO));
                //1025新增，之前活动开启时忘了打时间戳
                wfActivityHistoryInstanceMapper.insert(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivtityInstanceDO));
            }
        }
        return wfActivtityInstanceBOList;
    }

    @Override
    public ActivityInstanceBO addStartEventActivity(StartEvent startEvent, String piId, String pdId, String piStarter) {
        QueryWrapper<WfActivityInstanceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("pi_id",piId)
                .eq("usertask_no",startEvent.getNo());
        WfActivityInstanceDO wfActivtityInstanceDO = wfActivtityInstanceMapper.selectOne(queryWrapper);
        if(wfActivtityInstanceDO != null){
            wfActivtityInstanceDO.setAiAssignerId(JSON.toJSONString(piStarter));
            wfActivtityInstanceDO.setActiveTiNum(1);
            wfActivtityInstanceDO.setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING);
            wfActivtityInstanceDO.setUpdatetime(new Date());
            wfActivtityInstanceMapper.updateById(wfActivtityInstanceDO);
            //若之前活动产生了参数，则删除
            Map<String,Object> conditionMap = new HashMap<>();
            conditionMap.put("ai_id",wfActivtityInstanceDO.getId());
            wfProcessParamsRecordMapper.deleteByMap(conditionMap);
            //wfActivtityInstanceBOList.add(WfActivtityInstanceConvert.INSTANCE.convertDOToBO(wfActivtityInstanceDO));
            //状态转为运行，打时间戳
            wfActivityHistoryInstanceMapper.insert(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivtityInstanceDO));
        }else{
            wfActivtityInstanceDO = new WfActivityInstanceDO()
                    .setAiName(startEvent.getName())
                    .setAiStatus(ActivityInstanceState.TASK_ACTIVITY_STATE_RUNNING)
                    .setAiAssignerId(JSON.toJSONString(piStarter))
                    .setAiAssignerType("0")
                    .setUsertaskNo(startEvent.getNo())
                    .setAiCategory("01")
                    .setPiId(piId)
                    .setPdId(pdId)
                    .setActiveTiNum(1);
            wfActivtityInstanceDO.setCreatetime(new Date());
            wfActivtityInstanceDO.setUpdatetime(wfActivtityInstanceDO.getCreatetime());
            wfActivtityInstanceMapper.insert(wfActivtityInstanceDO);
            //wfActivtityInstanceBOList.add(WfActivtityInstanceConvert.INSTANCE.convertDOToBO(wfActivtityInstanceDO));
            //1025新增，之前活动开启时忘了打时间戳
            wfActivityHistoryInstanceMapper.insert(WfActivityInstanceConvert.INSTANCE.convertRunDOToHistoryDO(wfActivtityInstanceDO));
        }
        return WfActivityInstanceConvert.INSTANCE.convertDOToBO(wfActivtityInstanceDO);
    }
}
