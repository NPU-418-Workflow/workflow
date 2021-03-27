package me.danght.workflow.form.convert;

import me.danght.workflow.common.dto.ProcessParamsRelationDTO;
import me.danght.workflow.form.dataobject.ProcessParamsRelationDO;
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
