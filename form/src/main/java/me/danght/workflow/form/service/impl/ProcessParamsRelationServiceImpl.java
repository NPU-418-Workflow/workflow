package me.danght.workflow.form.service.impl;

import me.danght.workflow.common.api.form.ProcessParamsRelationService;
import me.danght.workflow.common.dto.ProcessParamsRelationDTO;
import me.danght.workflow.form.convert.ProcessParamsRelationConvert;
import me.danght.workflow.form.dao.ProcessParamsRelationRepository;
import me.danght.workflow.form.dataobject.ProcessParamsRelationDO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

@DubboService(interfaceClass = ProcessParamsRelationService.class)
@ApplicationScoped
@ActivateRequestContext
public class ProcessParamsRelationServiceImpl implements ProcessParamsRelationService {

    @Inject
    ProcessParamsRelationRepository processParamsRelationRepository;

    @Override
    public ProcessParamsRelationDTO save(ProcessParamsRelationDTO wfProcessParamsRelationDTO) {
        ProcessParamsRelationDO processParamsRelationDO = ProcessParamsRelationConvert.INSTANCE.convertDTOToDO(wfProcessParamsRelationDTO);
        processParamsRelationRepository.save(processParamsRelationDO);
        return ProcessParamsRelationConvert.INSTANCE.convertDOToDTO(processParamsRelationDO);
    }

    @Override
    public ProcessParamsRelationDTO getEnginePpName(String pdId, String businessName, String taskNo) {
        ProcessParamsRelationDO processParamsRelationDO = processParamsRelationRepository
                .findByBusinessNameAndPdIdAndTaskNo(
                        businessName,
                        pdId,
                        taskNo
                ).orElse(null);
        return ProcessParamsRelationConvert.INSTANCE.convertDOToDTO(processParamsRelationDO);
    }
}
