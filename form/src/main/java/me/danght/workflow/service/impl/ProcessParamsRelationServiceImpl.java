package me.danght.workflow.service.impl;

import me.danght.workflow.common.api.form.ProcessParamsRelationService;
import me.danght.workflow.common.dto.ProcessParamsRelationDTO;
import me.danght.workflow.convert.ProcessParamsRelationConvert;
import me.danght.workflow.dao.ProcessParamsRelationMapper;
import me.danght.workflow.dataobject.ProcessParamsRelationDO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.inject.Inject;
import javax.inject.Singleton;

@DubboService(interfaceClass = ProcessParamsRelationService.class)
@Singleton
public class ProcessParamsRelationServiceImpl implements ProcessParamsRelationService {

    @Inject
    ProcessParamsRelationMapper processParamsRelationMapper;

    @Override
    public ProcessParamsRelationDTO save(ProcessParamsRelationDTO wfProcessParamsRelationDTO) {
        ProcessParamsRelationDO processParamsRelationDO = ProcessParamsRelationConvert.INSTANCE.convertDTOToDO(wfProcessParamsRelationDTO);
        processParamsRelationMapper.save(processParamsRelationDO);
        return ProcessParamsRelationConvert.INSTANCE.convertDOToDTO(processParamsRelationDO);
    }

    @Override
    public ProcessParamsRelationDTO getEnginePpName(String pdId, String businessName, String taskNo) {
        ProcessParamsRelationDO processParamsRelationDO = processParamsRelationMapper
                .findByBusinessNameAndPdIdAndTaskNo(
                        businessName,
                        pdId,
                        taskNo
                ).orElse(null);
        return ProcessParamsRelationConvert.INSTANCE.convertDOToDTO(processParamsRelationDO);
    }
}
