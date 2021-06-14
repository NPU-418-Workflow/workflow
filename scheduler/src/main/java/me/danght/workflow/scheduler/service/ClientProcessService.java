package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.bo.ProcessDefinitionBO;
import me.danght.workflow.scheduler.bo.ProcessInstanceBO;
import me.danght.workflow.scheduler.dataobject.ParmObject;

import java.util.List;
import java.util.Map;

public interface ClientProcessService {
    boolean startProcess(String pdId, String piName, String piStarter, String piBusinesskey, Map<String, ParmObject> requiredData);
    List<ProcessDefinitionBO> queryDefinitionList();
    void changeProcessState(String piId, String state);
    List<ProcessInstanceBO> getProcessListByUserId(String piStarter);
}
