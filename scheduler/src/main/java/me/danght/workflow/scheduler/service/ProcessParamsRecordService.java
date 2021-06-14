package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.bo.ProcessParamsRecordBO;
import me.danght.workflow.scheduler.dataobject.ParmObject;
import me.danght.workflow.scheduler.dto.ActivityInstanceDTO;

import java.util.Map;

public interface ProcessParamsRecordService {
    void recordRequiredData(String aiId, String tiId,Map<String, ParmObject> requiredData);

    @Deprecated
    void calculateActivityData(ActivityInstanceDTO activityInstanceDTO, String tiId);

    ProcessParamsRecordBO getByEnginePpName(String enginePpName, String processInstanceId, String pdId, String userTaskNo);
}
