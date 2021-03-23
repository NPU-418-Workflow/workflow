package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.constant.ActivityInstanceCategory;
import me.danght.workflow.common.dataobject.ParmObject;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.bo.ProcessParamsRecordBO;
import me.danght.workflow.scheduler.constant.ProcessParamRecordLevel;
import me.danght.workflow.scheduler.constant.ProcessParamState;
import me.danght.workflow.scheduler.convert.ProcessParamsRecordConvert;
import me.danght.workflow.scheduler.dao.ProcessParamsRecordRepository;
import me.danght.workflow.scheduler.dataobject.ProcessParamsRecordDO;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

/**
 * 流程参数记录服务
 *
 * @author wenxiang
 * @author DangHT
 */
@ApplicationScoped
public class ProcessParamsRecordServiceImpl implements ProcessParamsRecordService {

    @Inject
    ProcessParamsRecordRepository processParamsRecordRepository;

    @Inject
    ActivityInstanceService activityInstanceService;

    @Override
    public void recordRequiredData(String aiId, String tiId,Map<String, ParmObject> requiredData) {
        //新版本逻辑为每个任务的参数过来直接计算为活动级别参数，当然也记录任务参数
        if(requiredData == null)
            return;
        for (Map.Entry<String, ParmObject> entry : requiredData.entrySet()) {
            ProcessParamsRecordDO tiRecord  = processParamsRecordRepository.findByEnginePpNameAndTiId(entry.getKey(), tiId).orElse(null);
            if(tiRecord != null) return;
            String val;
            if(entry.getValue().getVal() instanceof String)
                val = (String) entry.getValue().getVal();
            else if (entry.getValue().getVal() instanceof Boolean)
                val = ((Boolean) (entry.getValue().getVal())) ? "1" : "0";
            else
                val = String.valueOf(entry.getValue().getVal());
            //任务数据记录
            ProcessParamsRecordDO processParamsRecordDOTask = new ProcessParamsRecordDO();
            processParamsRecordDOTask.setTiId(tiId)
                    .setPpRecordValue(val)
                    .setEnginePpName(entry.getKey())
                    .setStatus(ProcessParamState.PROCESS_PARAM_EFFECT)
                    .setPpType(entry.getValue().getPpType())
                    .setUpdateTime(new Date());
            processParamsRecordRepository.save(processParamsRecordDOTask);

            ProcessParamsRecordDO processParamsRecordDO = processParamsRecordRepository.findByEnginePpNameAndAiId(entry.getKey(), aiId).orElse(null);
            if(processParamsRecordDO == null){
                processParamsRecordDO = new ProcessParamsRecordDO();
                processParamsRecordDO.setAiId(aiId)
                        .setPpRecordValue(val)
                        .setEnginePpName(entry.getKey())
                        .setStatus(ProcessParamState.PROCESS_PARAM_EFFECT)
                        .setPpType(entry.getValue().getPpType())
                        .setUpdateTime(new Date());
                processParamsRecordRepository.save(processParamsRecordDO);
            }else{
                processParamsRecordDO.setPpRecordValue(val);
                processParamsRecordRepository.save(processParamsRecordDO);
            }
            //TODO 此处还应更新到缓存，由于返回值得问题暂时还没处理，另外还有并发下数据一致性的问题
        }
    }

    @Override
    public void calculateActivityData(ActivityInstanceDTO activityInstanceDTO, String tiId){
        //普通活动，直接把任务参数复制一下弄成活动参数就行啦
        if(activityInstanceDTO.getAiCategory().equals(ActivityInstanceCategory.ACTIVITY_CATEGORY_SINGLE)){
            List<ProcessParamsRecordDO> processParamsRecordDOList = processParamsRecordRepository
                    .findAllByTiIdAndStatusAndPpRecordLevel(tiId, ProcessParamState.PROCESS_PARAM_EFFECT, ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_TASK);
            for(ProcessParamsRecordDO processParamsRecordDO : processParamsRecordDOList){
                String deleteId = processParamsRecordDO.getId();
                //TODO 1025新增之前如果有驳回的循环操作生成过改活动级别参数的话就先置失效，后面有了历史库再挪过去
                List<ProcessParamsRecordDO> processParamsRecordDOPreList = processParamsRecordRepository
                        .findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(
                                processParamsRecordDO.getPpRelationId(),
                                processParamsRecordDO.getAiId(),
                                ProcessParamState.PROCESS_PARAM_EFFECT,
                                ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                for(ProcessParamsRecordDO wfProcessParamsRecordPreDO : processParamsRecordDOPreList){
                    wfProcessParamsRecordPreDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    processParamsRecordRepository.save(wfProcessParamsRecordPreDO);
                }
                processParamsRecordDO.setId(null);
                processParamsRecordDO.setTiId(null);
                processParamsRecordDO.setAiId(activityInstanceDTO.getId());
                processParamsRecordDO.setCreateTime(new Date());
                processParamsRecordDO.setUpdateTime(processParamsRecordDO.getCreateTime());
                processParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_EFFECT);
                processParamsRecordDO.setPpRecordLevel(ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                processParamsRecordRepository.save(processParamsRecordDO);
                //TODO 后续增加参数历史表后将任务数据记录在历史表中
                //1025新增，生成活动数据后就删除相关任务数据
                processParamsRecordRepository.deleteById(deleteId);
            }
        }else {//TODO 会签活动，所有任务数据相与，暂时版本，待优化
            List<ProcessParamsRecordDO> taskLevelRecordList = processParamsRecordRepository.findAllByTiId(tiId);
            List<ProcessParamsRecordDO> activityRecordList = new ArrayList<>();
            for(ProcessParamsRecordDO processParamsRecordDO : taskLevelRecordList){
                processParamsRecordDO.setUpdateTime(new Date());
                processParamsRecordDO.setCreateTime(processParamsRecordDO.getUpdateTime());
                processParamsRecordDO.setTiId(null);
                processParamsRecordDO.setAiId(activityInstanceDTO.getId());
                processParamsRecordDO.setPpRecordLevel(ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY);
                processParamsRecordDO.setPpRecordValue("0");
                activityRecordList.add(processParamsRecordDO);
            }

            //计算活动级参数并存储
            for(ProcessParamsRecordDO activityRecordDO : activityRecordList){
                activityRecordDO.setId(null);
                List<ProcessParamsRecordDO> taskRecordDoList = processParamsRecordRepository
                        .findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(
                                activityRecordDO.getPpRelationId(),
                                activityRecordDO.getAiId(),
                                ProcessParamState.PROCESS_PARAM_EFFECT,
                                ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_TASK
                        );
                for(ProcessParamsRecordDO processParamsRecordDO : taskRecordDoList){
                    int taskValue = Integer.parseInt(processParamsRecordDO.getPpRecordValue());
                    int activityValue = Integer.parseInt(activityRecordDO.getPpRecordValue());
                    activityRecordDO.setPpRecordValue(String.valueOf(taskValue + activityValue));
                    processParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    processParamsRecordRepository.save(processParamsRecordDO);
                    processParamsRecordRepository.deleteById(processParamsRecordDO.getId());
                    //TODO 后期增加参数历史表，将参数记录移到历史表
                }
                /**
                 * 驳回情况些可能会出现一个参数多条记录，虽然在取得时候也做了只取最新的一条的
                 * 限制，但还是也把之前的置为失效，安全起见。
                 * TODO 其实这应该删除之前的活动数据，并转移到历史库
                 */
                List<ProcessParamsRecordDO> processParamsRecordDOList = processParamsRecordRepository
                        .findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(
                                activityRecordDO.getPpRelationId(),
                                activityRecordDO.getAiId(),
                                ProcessParamState.PROCESS_PARAM_EFFECT,
                                ProcessParamRecordLevel.PROCESS_PARAM_RECORD_LEVEL_ACTIVITY
                        );
                for(ProcessParamsRecordDO processParamsRecordDO : processParamsRecordDOList){
                    processParamsRecordDO.setStatus(ProcessParamState.PROCESS_PARAM_FAILURE);
                    processParamsRecordRepository.save(processParamsRecordDO);
                }
                processParamsRecordRepository.save(activityRecordDO);
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
    public ProcessParamsRecordBO getByEnginePpName(String enginePpName, String processInstanceId, String pdId, String userTaskNo) {
        ActivityInstanceBO activityInstanceBO = activityInstanceService.getByPiIdAndUserTaskNo(processInstanceId, userTaskNo);
        List<ProcessParamsRecordDO> processParamsRecordDOList = processParamsRecordRepository
                .findAllByEnginePpNameAndAiIdAndStatusOrderByCreateTimeDesc(
                        enginePpName,
                        activityInstanceBO.getId(),
                        ProcessParamState.PROCESS_PARAM_EFFECT
                );
        return ProcessParamsRecordConvert.INSTANCE.convertDOToBO(processParamsRecordDOList.get(0));
    }

}
