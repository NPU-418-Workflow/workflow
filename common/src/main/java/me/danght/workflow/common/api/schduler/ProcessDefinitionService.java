package me.danght.workflow.common.api.schduler;

import me.danght.workflow.common.bo.ProcessDefinitionBO;
import me.danght.workflow.common.dto.ProcessTemplateDTO;

import java.util.List;

public interface ProcessDefinitionService {
    ProcessDefinitionBO getProcessDefinitionById(String id);
    ProcessDefinitionBO generatePDFromTemplateFile(ProcessTemplateDTO wfProcessTemplateDTO);
    List<ProcessDefinitionBO> queryDefinitionList();
}
