package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.dataobject.ProcessParamsRelationDO;
import me.danght.workflow.scheduler.dto.ProcessParamsRelationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ProcessParamsRelationConvert {
    ProcessParamsRelationConvert INSTANCE = Mappers.getMapper(ProcessParamsRelationConvert.class);

    @Mappings({})
    ProcessParamsRelationDTO convertDOToDTO(ProcessParamsRelationDO processParamsRelationDO);

    @Mappings({})
    ProcessParamsRelationDO convertDTOToDO(ProcessParamsRelationDTO wfProcessParamsRelationDTO);
}
