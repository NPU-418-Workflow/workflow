package me.danght.workflow.common.api.form;


import me.danght.workflow.common.dto.ProcessParamsRelationDTO;

public interface ProcessParamsRelationService {
    ProcessParamsRelationDTO save(ProcessParamsRelationDTO wfProcessParamsRelationDTO);

    ProcessParamsRelationDTO getEnginePpName(String pdId,String businessName,String taskNo);
}
