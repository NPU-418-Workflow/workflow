package me.danght.workflow.scheduler.service;

import me.danght.workflow.common.dto.ProcessTemplateDTO;

public interface ProcessTemplateService {
    ProcessTemplateDTO selectByPtFilename(String ptFilename);
}
