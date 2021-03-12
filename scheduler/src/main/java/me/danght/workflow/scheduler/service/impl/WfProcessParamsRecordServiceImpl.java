package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.constant.ActivityInstanceCategory;
import me.danght.workflow.common.dataobject.ParmObject;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.bo.WfProcessParamsRecordBO;
import me.danght.workflow.scheduler.constant.ProcessParamRecordLevel;
import me.danght.workflow.scheduler.constant.ProcessParamState;
import me.danght.workflow.scheduler.convert.WfProcessParamsRecordConvert;
import me.danght.workflow.scheduler.dao.WfProcessParamsRecordMapper;
import me.danght.workflow.scheduler.dao.redis.WfProcessParamsRecordCacheDao;
import me.danght.workflow.scheduler.dataobject.WfProcessParamsRecordDO;
import me.danght.workflow.scheduler.service.WfActivtityInstanceService;
import me.danght.workflow.scheduler.service.WfProcessParamsRecordService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class WfProcessParamsRecordServiceImpl implements WfProcessParamsRecordService {

    @Inject
    WfProcessParamsRecordMapper wfProcessParamsRecordMapper;

    @Inject
    WfActivtityInstanceService wfActivtityInstanceService;

    @Inject
    WfProcessParamsRecordCacheDao wfProcessParamsRecordCacheDao;

    @Override
    public void recordRequiredData(String aiId, String tiId,Map<String, ParmObject> requiredData) {
        //新版本逻辑为每个任务的参数过来直接计算为活动级别参数，当然也记录任务参数
        if(requiredData == null)
            return;
        for (Map.Entry<String, ParmObject> entry : requiredData.entrySet()) {
            WfProcessParamsRecordDO tiRecord  = wfProcessParamsRecordMapper.findByEnginePpNameAndTiId(entry.getKey(), tiId).get();
            if(tiRecord != null) return;
            String val;
            if(entry.getValue().getVal() instanceof String)
                val = (String) entry.getValue().getVal();
            else if (entry.getValue().getVal() instanceof Boolean)
                val = ((Boolean) (entry.getValue().getVal())) ? "1" : "0";
            else
                val = String.valueOf(entry.getValue().getVal());
            //任务数据记录
            WfProcessParamsRecordDO wfProcessParamsRecordDOTask = new WfProcessParamsRecordDO();
            wfProcessParamsRecordDOTask.setTiId(tiId)
                    .setPpRecordValue(val)
                    .setEnginePpName(entry.getKey())
                    .setStatus(ProcessParamState.PROCESS_PARAM_EFFECT)
                    .setPpType(entry.getValue().getPpType())
                    .setUpdatetime(new Date());
            wfProcessParamsRecordMapper.save(wfProcessParamsRecordDOTask);

            WfProcessParamsRecordDO wfProcessParamsRecordDO = wfProcessParamsRecordMapper.findByEnginePpNameAndAiId(entry.getKey(), aiId).get();
            if(wfProcessParamsRecordDO == null){
                wfProcessParamsRecordDO = new WfProcessParamsRecordDO();
                wfProcessParamsRecordDO.setAiId(aiId)
                        .setPpRecordValue(val)
                        .setEnginePpName(entry.getKey())
                        .setStatus(ProcessParamState.PROCESS_PARAM_EFFECT)
                        .setPpType(entry.getValue().getPpType())
                        .setUpdatetime(new Date());
                wfProcessParamsRecordMapper.save(wfProcessParamsRecordDO);
            }else{
                wfProcessParamsRecordDO.setPpRecordValue(val);
                wfProcessParamsRecordMapper.save(wfProcessParamsRecordDO);
            }
            //TODO 此处还应更新到缓存，由于返回值得问题暂时还没处理，另外还有并发下数据一致性的问题
        }
    }

    @Override
    public void calculateActivityData(ActivityInstanceDTO wfActivtityInstanceDTO, String tiId){
        //普通活动，直接把任务参数复制一下弄成活动参数就行啦
        if(wfActivtityInstanceDTO.getAiCategory().equals(ActivityInstanceCategory.ACTIVITY_CATEGORY_SINGLE)){
            List<WfProcessParamsRecordDO> wfProcessParamsRecordDOList = (List<WfProcessParamsRecordDO>) wfProcessParamsRecordMapper
                    .findAllByTiIdAndStatusAndPpRecordLevel(tiId, ProcessParamState.PROCESS_PARAM_EFFECT, ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_TASK);
            for(WfProcessParamsRecordDO wfProcessParamsRecordDO : wfProcessParamsRecordDOList){
                String deleteId = wfProcessParamsRecordDO.getId();
                //TODO 1025新增之前如果有驳回的循环操作生成过改活动级别参数的话就先置失效，后面有了历史库再挪过去
                List<WfProcessParamsRecordDO> wfProcessParamsRecordDOPreList = (List<WfProcessParamsRecordDO>) wfProcessParamsRecordMapper
                        .findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(
                                wfProcessParamsRecordDO.getPpRelationId(),
                                wfProcessParamsRecordDO.getAiId(),
                                ProcessParamState.PROCESS_PARAM_EFFECT,
                                ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                for(WfProcessParamsRecordDO wfProcessParamsRecordPreDO : wfProcessParamsRecordDOPreList){
                    wfProcessParamsRecordPreDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    wfProcessParamsRecordMapper.save(wfProcessParamsRecordPreDO);
                }
                wfProcessParamsRecordDO.setId(null);
                wfProcessParamsRecordDO.setTiId(null);
                wfProcessParamsRecordDO.setAiId(wfActivtityInstanceDTO.getId());
                wfProcessParamsRecordDO.setCreatetime(new Date());
                wfProcessParamsRecordDO.setUpdatetime(wfProcessParamsRecordDO.getCreatetime());
                wfProcessParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_EFFECT);
                wfProcessParamsRecordDO.setPpRecordLevel(ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                wfProcessParamsRecordMapper.save(wfProcessParamsRecordDO);
                //TODO 后续增加参数历史表后将任务数据记录在历史表中
                //1025新增，生成活动数据后就删除相关任务数据
                wfProcessParamsRecordMapper.deleteById(deleteId);
            }
        }else {//TODO 会签活动，所有任务数据相与，暂时版本，待优化
            List<WfProcessParamsRecordDO> taskLevelRecordList = (List<WfProcessParamsRecordDO>) wfProcessParamsRecordMapper.findAllByTiId(tiId);
            List<WfProcessParamsRecordDO> activityRecordList = new ArrayList<>();
            for(WfProcessParamsRecordDO wfProcessParamsRecordDO : taskLevelRecordList){
                wfProcessParamsRecordDO.setUpdatetime(new Date());
                wfProcessParamsRecordDO.setCreatetime(wfProcessParamsRecordDO.getUpdatetime());
                wfProcessParamsRecordDO.setTiId(null);
                wfProcessParamsRecordDO.setAiId(wfActivtityInstanceDTO.getId());
                wfProcessParamsRecordDO.setPpRecordLevel(ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                wfProcessParamsRecordDO.setPpRecordValue("0");
                activityRecordList.add(wfProcessParamsRecordDO);
            }

            //计算活动级参数并存储
            for(WfProcessParamsRecordDO activityRecordDO : activityRecordList){
                activityRecordDO.setId(null);
                List<WfProcessParamsRecordDO> taskRecordDoList = (List<WfProcessParamsRecordDO>) wfProcessParamsRecordMapper
                        .findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(
                                activityRecordDO.getPpRelationId(),
                                activityRecordDO.getAiId(),
                                ProcessParamState.PROCESS_PARAM_EFFECT,
                                ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_TASK
                        );
                for(WfProcessParamsRecordDO wfProcessParamsRecordDO : taskRecordDoList){
                    int taskValue = Integer.parseInt(wfProcessParamsRecordDO.getPpRecordValue());
                    int activityValue = Integer.parseInt(activityRecordDO.getPpRecordValue());
                    activityRecordDO.setPpRecordValue(String.valueOf(taskValue + activityValue));
                    wfProcessParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    wfProcessParamsRecordMapper.save(wfProcessParamsRecordDO);
                    wfProcessParamsRecordMapper.deleteById(wfProcessParamsRecordDO.getId());
                    //TODO 后期增加参数历史表，将参数记录移到历史表
                }
                /**
                 * 驳回情况些可能会出现一个参数多条记录，虽然在取得时候也做了只取最新的一条的
                 * 限制，但还是也把之前的置为失效，安全起见。
                 * TODO 其实这应该删除之前的活动数据，并转移到历史库
                 */
                List<WfProcessParamsRecordDO> wfProcessParamsRecordDOList = (List<WfProcessParamsRecordDO>) wfProcessParamsRecordMapper
                        .findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(
                                activityRecordDO.getPpRelationId(),
                                activityRecordDO.getAiId(),
                                ProcessParamState.PROCESS_PARAM_EFFECT,
                                ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY
                        );
                for(WfProcessParamsRecordDO wfProcessParamsRecordDO : wfProcessParamsRecordDOList){
                    wfProcessParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    wfProcessParamsRecordMapper.save(wfProcessParamsRecordDO);
                }
                wfProcessParamsRecordMapper.save(activityRecordDO);
            }


        }
    }

    /**
     * TODO 重要，由于目前没有模拟引擎唯一参数的生成，是直接手动写的，所以xml的参数格式比较详细，而且目前是能够做到只要
     * 引擎生成的参数名称是流程内唯一的就可以的，但是如果引擎生成全库唯一的参数的话，再加上在record表引入全都写上piid，
     * 可以优化这个方法
     * @param enginePpName
     * @param processInstanceId
     * @param pdId
     * @param userTaskNo
     * @return
     */
    @Override
    public WfProcessParamsRecordBO getByEnginePpName(String enginePpName, String processInstanceId, String pdId, String userTaskNo) {
        ActivityInstanceBO wfActivityInstanceBO = wfActivtityInstanceService.getByPiIdAndUserTaskNo(processInstanceId, userTaskNo);
        List<WfProcessParamsRecordDO> wfProcessParamsRecordDOList = (List<WfProcessParamsRecordDO>) wfProcessParamsRecordMapper
                .findAllByEnginePpNameAndAiIdOrderByCreatetimeDesc(
                        enginePpName,
                        wfActivityInstanceBO.getId(),
                        ProcessParamState.PROCESS_PARAM_EFFECT
                );
        return WfProcessParamsRecordConvert.INSTANCE.convertDOToBO(wfProcessParamsRecordDOList.get(0));
    }

}
