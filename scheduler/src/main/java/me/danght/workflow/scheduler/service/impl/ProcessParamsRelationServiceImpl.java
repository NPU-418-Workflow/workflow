package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.convert.ProcessParamsRelationConvert;
import me.danght.workflow.scheduler.dao.ProcessParamsRelationRepository;
import me.danght.workflow.scheduler.dataobject.ProcessParamsRelationDO;
import me.danght.workflow.scheduler.dto.ProcessParamsRelationDTO;
import me.danght.workflow.scheduler.service.ProcessParamsRelationService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

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
