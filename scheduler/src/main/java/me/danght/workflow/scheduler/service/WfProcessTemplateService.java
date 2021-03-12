package me.danght.workflow.scheduler.service;

import me.danght.workflow.common.dto.ProcessTemplateDTO;

public interface WfProcessTemplateService {
    ProcessTemplateDTO selectByPtFilename(String ptFilename);
}
