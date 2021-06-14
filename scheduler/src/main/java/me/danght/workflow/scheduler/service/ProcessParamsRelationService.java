package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.dto.ProcessParamsRelationDTO;

public interface ProcessParamsRelationService {
    ProcessParamsRelationDTO save(ProcessParamsRelationDTO wfProcessParamsRelationDTO);

    ProcessParamsRelationDTO getEnginePpName(String pdId,String businessName,String taskNo);
}
