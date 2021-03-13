package me.danght.workflow.form.convert;

import me.danght.workflow.form.dataobject.BusinessFormDO;
import me.danght.workflow.form.dto.BusinessFormDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessFormConvert {
    BusinessFormConvert INSTANCE = Mappers.getMapper(BusinessFormConvert.class);

    @Mappings({})
    BusinessFormDTO convertDOToDTO(BusinessFormDO businessFormDO);

    @Mappings({})
    BusinessFormDO convertDTOToDO(BusinessFormDTO businessFormDTO);
}
