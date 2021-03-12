package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.dto.ProcessTemplateDTO;
import me.danght.workflow.scheduler.convert.ProcessTemplateConvert;
import me.danght.workflow.scheduler.dao.ProcessTemplateRepository;
import me.danght.workflow.scheduler.dataobject.ProcessTemplateDO;
import me.danght.workflow.scheduler.service.ProcessTemplateService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProcessTemplateServiceImpl implements ProcessTemplateService {

    @Inject
    ProcessTemplateRepository processTemplateRepository;

    @Override
    public ProcessTemplateDTO selectByPtFilename(String ptFilename) {
        ProcessTemplateDO processTemplateDO = processTemplateRepository.findByPtFilename(ptFilename).orElse(null);
        return ProcessTemplateConvert.INSTANCE.convertDOToDTO(processTemplateDO);
    }
}
