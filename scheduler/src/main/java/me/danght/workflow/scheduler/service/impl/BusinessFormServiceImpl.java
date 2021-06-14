package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.convert.BusinessFormConvert;
import me.danght.workflow.scheduler.dao.BusinessFormRepository;
import me.danght.workflow.scheduler.dataobject.BusinessFormDO;
import me.danght.workflow.scheduler.dto.BusinessFormDTO;
import me.danght.workflow.scheduler.service.BusinessFormService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

/**
 * 业务表单服务
 *
 * @author wenxiang
 * @author DangHT
 */
@ApplicationScoped
@ActivateRequestContext
public class BusinessFormServiceImpl implements BusinessFormService {

    @Inject
    BusinessFormRepository businessFormRepository;

    @Override
    public BusinessFormDTO selectById(String id) {
        BusinessFormDO businessFormDO = businessFormRepository.findById(id).orElse(null);
        return BusinessFormConvert.INSTANCE.convertDOToDTO(businessFormDO);
    }
}
