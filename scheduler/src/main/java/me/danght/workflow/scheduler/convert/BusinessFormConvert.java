package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.dataobject.BusinessFormDO;
import me.danght.workflow.scheduler.dto.BusinessFormDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface BusinessFormConvert {
    BusinessFormConvert INSTANCE = Mappers.getMapper(BusinessFormConvert.class);

    @Mappings({})
    BusinessFormDTO convertDOToDTO(BusinessFormDO businessFormDO);

    @Mappings({})
    BusinessFormDO convertDTOToDO(BusinessFormDTO businessFormDTO);
}
