package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.bo.ProcessInstanceBO;
import me.danght.workflow.scheduler.dto.ProcessInstanceStartDTO;

import java.util.List;

public interface ProcessInstanceService {
    /**
     * 开启一个新流程
     * @param wfProcessInstanceStartDTO
     * @return
     */
    ProcessInstanceBO startProcess(ProcessInstanceStartDTO wfProcessInstanceStartDTO);

    /**
     * 结束流程并收尾
     */
    void endProcess(String piId);

    void changeProcessState(String piId, String state);

    ProcessInstanceBO getById(String id);

    List<ProcessInstanceBO> getProcessListByUserId(String piStarter);
}