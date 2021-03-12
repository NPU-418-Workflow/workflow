package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.dto.ProcessTemplateDTO;
import me.danght.workflow.scheduler.convert.WfProcessTemplateConvert;
import me.danght.workflow.scheduler.dao.WfProcessTemplateMapper;
import me.danght.workflow.scheduler.dataobject.WfProcessTemplateDO;
import me.danght.workflow.scheduler.service.WfProcessTemplateService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WfProcessTemplateServiceImpl implements WfProcessTemplateService {

    @Inject
    WfProcessTemplateMapper wfProcessTemplateMapper;

    @Override
    public ProcessTemplateDTO selectByPtFilename(String ptFilename) {
        WfProcessTemplateDO wfProcessTemplateDO = wfProcessTemplateMapper.findByPtFilename(ptFilename).get();
        return WfProcessTemplateConvert.INSTANCE.convertDOToDTO(wfProcessTemplateDO);
    }
}
