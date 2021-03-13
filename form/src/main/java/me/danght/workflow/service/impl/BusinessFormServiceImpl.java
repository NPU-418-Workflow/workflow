package me.danght.workflow.service.impl;

import me.danght.workflow.convert.BusinessFormConvert;
import me.danght.workflow.dao.BusinessFormMapper;
import me.danght.workflow.dataobject.BusinessFormDO;
import me.danght.workflow.dto.BusinessFormDTO;
import me.danght.workflow.service.BusinessFormService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BusinessFormServiceImpl implements BusinessFormService {

    @Inject
    BusinessFormMapper businessFormMapper;

    @Override
    public BusinessFormDTO selectById(String id) {
        BusinessFormDO businessFormDO = businessFormMapper.findById(id).orElse(null);
        return BusinessFormConvert.INSTANCE.convertDOToDTO(businessFormDO);
    }
}
