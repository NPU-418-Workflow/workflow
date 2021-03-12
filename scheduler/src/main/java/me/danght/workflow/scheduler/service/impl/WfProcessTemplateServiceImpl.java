package me.danght.workflow.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oilpeddler.wfengine.common.dto.WfProcessTemplateDTO;
import com.oilpeddler.wfengine.schedulecomponent.convert.WfProcessTemplateConvert;
import com.oilpeddler.wfengine.schedulecomponent.dao.WfProcessTemplateMapper;
import com.oilpeddler.wfengine.schedulecomponent.dataobject.WfProcessTemplateDO;
import com.oilpeddler.wfengine.schedulecomponent.service.WfProcessTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WfProcessTemplateServiceImpl implements WfProcessTemplateService {
    @Autowired
    WfProcessTemplateMapper wfProcessTemplateMapper;

    @Override
    public WfProcessTemplateDTO selectByPtFilename(String ptFilename) {
        QueryWrapper<WfProcessTemplateDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pt_filename",ptFilename);
        WfProcessTemplateDO wfProcessTemplateDO = wfProcessTemplateMapper.selectOne(queryWrapper);
        return WfProcessTemplateConvert.INSTANCE.convertDOToDTO(wfProcessTemplateDO);
    }
}
