package me.danght.workflow.scheduler.service;

import me.danght.workflow.common.dataobject.ParmObject;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.bo.WfProcessParamsRecordBO;

import java.util.Map;

public interface WfProcessParamsRecordService {
    void recordRequiredData(String aiId, String tiId,Map<String, ParmObject> requiredData);

    @Deprecated
    void calculateActivityData(ActivityInstanceDTO wfActivityInstanceDTO, String tiId);

    WfProcessParamsRecordBO getByEnginePpName(String enginePpName, String processInstanceId, String pdId, String usertaskNo);
}
