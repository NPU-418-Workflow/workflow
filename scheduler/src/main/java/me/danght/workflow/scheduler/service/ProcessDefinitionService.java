package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.bo.ProcessDefinitionBO;
import me.danght.workflow.scheduler.dto.ProcessTemplateDTO;

import java.util.List;

public interface ProcessDefinitionService {
    ProcessDefinitionBO getProcessDefinitionById(String id);
    ProcessDefinitionBO generatePDFromTemplateFile(ProcessTemplateDTO wfProcessTemplateDTO);
    List<ProcessDefinitionBO> queryDefinitionList();
}
