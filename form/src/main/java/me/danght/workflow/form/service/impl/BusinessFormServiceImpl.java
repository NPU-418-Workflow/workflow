package me.danght.workflow.form.service.impl;

import me.danght.workflow.form.convert.BusinessFormConvert;
import me.danght.workflow.form.dao.BusinessFormRepository;
import me.danght.workflow.form.dataobject.BusinessFormDO;
import me.danght.workflow.form.dto.BusinessFormDTO;
import me.danght.workflow.form.service.BusinessFormService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BusinessFormServiceImpl implements BusinessFormService {

    @Inject
    BusinessFormRepository businessFormRepository;

    @Override
    public BusinessFormDTO selectById(String id) {
        BusinessFormDO businessFormDO = businessFormRepository.findById(id).orElse(null);
        return BusinessFormConvert.INSTANCE.convertDOToDTO(businessFormDO);
    }
}
