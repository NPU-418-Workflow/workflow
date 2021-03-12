package me.danght.workflow.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oilpeddler.wfengine.common.bo.WfActivtityInstanceBO;
import com.oilpeddler.wfengine.common.constant.ActivityInstanceCategory;
import com.oilpeddler.wfengine.common.dataobject.ParmObject;
import com.oilpeddler.wfengine.common.dto.WfActivtityInstanceDTO;
import com.oilpeddler.wfengine.schedulecomponent.bo.WfProcessParamsRecordBO;
import com.oilpeddler.wfengine.schedulecomponent.constant.ProcessParamRecordLevel;
import com.oilpeddler.wfengine.schedulecomponent.constant.ProcessParamState;
import com.oilpeddler.wfengine.schedulecomponent.convert.WfProcessParamsRecordConvert;
import com.oilpeddler.wfengine.schedulecomponent.dao.WfProcessParamsRecordMapper;
import com.oilpeddler.wfengine.schedulecomponent.dao.redis.WfProcessParamsRecordCacheDao;
import com.oilpeddler.wfengine.schedulecomponent.dataobject.WfProcessParamsRecordDO;
import com.oilpeddler.wfengine.schedulecomponent.service.WfActivtityInstanceService;
import com.oilpeddler.wfengine.schedulecomponent.service.WfProcessParamsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WfProcessParamsRecordServiceImpl implements WfProcessParamsRecordService {

    @Autowired
    WfProcessParamsRecordMapper wfProcessParamsRecordMapper;

/*    @Autowired
    WfProcessInstanceMapper wfProcessInstanceMapper;*/

/*    @Reference
    WfTaskInstanceService wfTaskInstanceService;*/

    @Autowired
    WfActivtityInstanceService wfActivtityInstanceService;


    @Autowired
    WfProcessParamsRecordCacheDao wfProcessParamsRecordCacheDao;

    @Override
    public void recordRequiredData(String aiId, String tiId,Map<String, ParmObject> requiredData) {
        //新版本逻辑为每个任务的参数过来直接计算为活动级别参数，当然也记录任务参数
        if(requiredData == null)
            return;
        for (Map.Entry<String, ParmObject> entry : requiredData.entrySet()) {
            /*WfProcessParamsRelationDO wfProcessParamsRelationDO = wfProcessParamsRelationCacheDao.get(entry.getKey(),pdId);
            if(wfProcessParamsRelationDO == null){
                QueryWrapper<WfProcessParamsRelationDO> queryWrapper = new QueryWrapper<>();
                queryWrapper
                        .eq("business_name",entry.getKey())
                        .eq("pd_id",pdId)
                        .eq("task_no",taskNo);
                wfProcessParamsRelationDO = wfProcessParamsRelationMapper.selectOne(queryWrapper);
            }
            if(wfProcessParamsRelationDO == null){
                continue;
            }*/
            //查询表中是否有任务记录，从而判重，保证幂等性
            QueryWrapper<WfProcessParamsRecordDO> queryWrapperRecord = new QueryWrapper<>();
            queryWrapperRecord.eq("engine_pp_name",entry.getKey())
                    .eq("ti_id",tiId);
            WfProcessParamsRecordDO tiRecord  = wfProcessParamsRecordMapper.selectOne(queryWrapperRecord);
            if(tiRecord != null)
                return;
            String val;
            if(entry.getValue().getVal() instanceof String)
                val = (String) entry.getValue().getVal();
            else if (entry.getValue().getVal() instanceof Boolean)
                val = ((Boolean)(entry.getValue().getVal())) == true ? "1" : "0";
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
            wfProcessParamsRecordMapper.insert(wfProcessParamsRecordDOTask);

            QueryWrapper<WfProcessParamsRecordDO> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("engine_pp_name",entry.getKey())
                    .eq("ai_id",aiId);
            WfProcessParamsRecordDO wfProcessParamsRecordDO = wfProcessParamsRecordMapper.selectOne(queryWrapper);
            if(wfProcessParamsRecordDO == null){
                wfProcessParamsRecordDO = new WfProcessParamsRecordDO();
                wfProcessParamsRecordDO.setAiId(aiId)
                        .setPpRecordValue(val)
                        .setEnginePpName(entry.getKey())
                        .setStatus(ProcessParamState.PROCESS_PARAM_EFFECT)
                        .setPpType(entry.getValue().getPpType())
                        .setUpdatetime(new Date());
                wfProcessParamsRecordMapper.insert(wfProcessParamsRecordDO);
            }else{
                wfProcessParamsRecordDO.setPpRecordValue(val);
                wfProcessParamsRecordMapper.updateParamsValue(wfProcessParamsRecordDO);
            }
            //TODO 此处还应更新到缓存，由于返回值得问题暂时还没处理，另外还有并发下数据一致性的问题
        }
    }

    @Override
    public void calculateActivityData(WfActivtityInstanceDTO wfActivtityInstanceDTO, String tiId){
        //普通活动，直接把任务参数复制一下弄成活动参数就行啦
        if(wfActivtityInstanceDTO.getAiCategory().equals(ActivityInstanceCategory.ACTIVITY_CATEGORY_SINGLE)){
            Map<String,Object> conditionMap = new HashMap<>();
            conditionMap.put("ti_id",tiId);
            conditionMap.put("status",ProcessParamState.PROCESS_PARAM_EFFECT);
            conditionMap.put("pp_record_level",ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_TASK);
            List<WfProcessParamsRecordDO> wfProcessParamsRecordDOList = wfProcessParamsRecordMapper.selectByMap(conditionMap);
            for(WfProcessParamsRecordDO wfProcessParamsRecordDO : wfProcessParamsRecordDOList){
                String deleteId = wfProcessParamsRecordDO.getId();
                //TODO 1025新增之前如果有驳回的循环操作生成过改活动级别参数的话就先置失效，后面有了历史库再挪过去
                QueryWrapper<WfProcessParamsRecordDO> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pp_relation_id", wfProcessParamsRecordDO.getPpRelationId());
                queryWrapper.eq("ai_id", wfProcessParamsRecordDO.getAiId());
                queryWrapper.eq("status",ProcessParamState.PROCESS_PARAM_EFFECT);
                queryWrapper.eq("pp_record_level",ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                List<WfProcessParamsRecordDO> wfProcessParamsRecordDOPreList = wfProcessParamsRecordMapper.selectList(queryWrapper);
                for(WfProcessParamsRecordDO wfProcessParamsRecordPreDO : wfProcessParamsRecordDOPreList){
                    wfProcessParamsRecordPreDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    wfProcessParamsRecordMapper.updateById(wfProcessParamsRecordPreDO);
                }
                wfProcessParamsRecordDO.setId(null);
                wfProcessParamsRecordDO.setTiId(null);
                wfProcessParamsRecordDO.setAiId(wfActivtityInstanceDTO.getId());
                wfProcessParamsRecordDO.setCreatetime(new Date());
                wfProcessParamsRecordDO.setUpdatetime(wfProcessParamsRecordDO.getCreatetime());
                wfProcessParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_EFFECT);
                wfProcessParamsRecordDO.setPpRecordLevel(ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                wfProcessParamsRecordMapper.insert(wfProcessParamsRecordDO);
                //TODO 后续增加参数历史表后将任务数据记录在历史表中
                //1025新增，生成活动数据后就删除相关任务数据
                wfProcessParamsRecordMapper.deleteById(deleteId);
            }
        }else {//TODO 会签活动，所有任务数据相与，暂时版本，待优化
            //List<WfTaskInstanceDTO> wfTaskInstanceDTOList = wfTaskInstanceService.findRelatedTaskList(wfActivtityInstanceDTO.getId());
            //逻辑上应该不会出现这种活动没有相关任务的情况
/*            if(wfTaskInstanceDTOList.size() == 0)
                return;*/
            Map<String,Object> conditionMap = new HashMap<>();
            QueryWrapper<WfProcessParamsRecordDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ti_id", tiId);
            List<WfProcessParamsRecordDO> taskLevelRecordList = wfProcessParamsRecordMapper.selectList(queryWrapper);
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
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pp_relation_id", activityRecordDO.getPpRelationId());
                queryWrapper.eq("ai_id", activityRecordDO.getAiId());
                queryWrapper.eq("status",ProcessParamState.PROCESS_PARAM_EFFECT);
                queryWrapper.eq("pp_record_level",ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_TASK);
                List<WfProcessParamsRecordDO> taskRecordDoList = wfProcessParamsRecordMapper.selectList(queryWrapper);
                for(WfProcessParamsRecordDO wfProcessParamsRecordDO : taskRecordDoList){
                    int taskValue = Integer.parseInt(wfProcessParamsRecordDO.getPpRecordValue());
                    int activityValue = Integer.parseInt(activityRecordDO.getPpRecordValue());
                    activityRecordDO.setPpRecordValue(String.valueOf(taskValue + activityValue));
                    wfProcessParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    wfProcessParamsRecordMapper.updateById(wfProcessParamsRecordDO);
                    wfProcessParamsRecordMapper.deleteById(wfProcessParamsRecordDO.getId());
                    //TODO 后期增加参数历史表，将参数记录移到历史表
                }
                /**
                 * 驳回情况些可能会出现一个参数多条记录，虽然在取得时候也做了只取最新的一条的
                 * 限制，但还是也把之前的置为失效，安全起见。
                 * TODO 其实这应该删除之前的活动数据，并转移到历史库
                 */
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pp_relation_id", activityRecordDO.getPpRelationId());
                queryWrapper.eq("ai_id", activityRecordDO.getAiId());
                queryWrapper.eq("status",ProcessParamState.PROCESS_PARAM_EFFECT);
                queryWrapper.eq("pp_record_level",ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                List<WfProcessParamsRecordDO> wfProcessParamsRecordDOList = wfProcessParamsRecordMapper.selectList(queryWrapper);
                for(WfProcessParamsRecordDO wfProcessParamsRecordDO : wfProcessParamsRecordDOList){
                    wfProcessParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    wfProcessParamsRecordMapper.updateById(wfProcessParamsRecordDO);
                }
                wfProcessParamsRecordMapper.insert(activityRecordDO);
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
     * @param usertaskNo
     * @return
     */
    @Override
    public WfProcessParamsRecordBO getByEnginePpName(String enginePpName, String processInstanceId, String pdId, String usertaskNo) {
        Map<String,Object> conditionMap = new HashMap<>();
        conditionMap.put("pi_id",processInstanceId);
        conditionMap.put("usertask_no",usertaskNo);
        WfActivtityInstanceBO wfActivtityInstanceBO = wfActivtityInstanceService.getOneByMap(conditionMap);
        /*conditionMap = new HashMap<>();
        conditionMap.put("engine_pp_name",enginePpName);
        conditionMap.put("pd_id",pdId);
        conditionMap.put("task_no",usertaskNo);
        List<WfProcessParamsRelationDO> wfProcessParamsRelationDOList = wfProcessParamsRelationMapper.selectByMap(conditionMap);*/
        conditionMap = new HashMap<>();
        conditionMap.put("engine_pp_name",enginePpName);
        conditionMap.put("ai_id",wfActivtityInstanceBO.getId());
        //流程算法只会读取活动级别的数据
        //conditionMap.put("pp_record_level",ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
        conditionMap.put("status",ProcessParamState.PROCESS_PARAM_EFFECT);
        QueryWrapper<WfProcessParamsRecordDO> queryWrapper = new QueryWrapper<>();
        //为了兼容驳回的情况，取最新的orderByDesc
        queryWrapper.allEq(conditionMap).orderByDesc("createtime");
        List<WfProcessParamsRecordDO> wfProcessParamsRecordDOList = wfProcessParamsRecordMapper.selectList(queryWrapper);
        WfProcessParamsRecordBO wfProcessParamsRecordBO =  WfProcessParamsRecordConvert.INSTANCE.convertDOToBO(wfProcessParamsRecordDOList.get(0));
        //wfProcessParamsRecordBO.setPpType(wfProcessParamsRelationDOList.get(0).getPpType());
        return wfProcessParamsRecordBO;
    }

}
