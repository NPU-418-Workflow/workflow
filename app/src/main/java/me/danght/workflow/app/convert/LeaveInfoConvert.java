package me.danght.workflow.app.convert;

import me.danght.workflow.app.dataobject.LeaveInfoDO;
import me.danght.workflow.app.dto.LeaveInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface LeaveInfoConvert {
    LeaveInfoConvert INSTANCE = Mappers.getMapper(LeaveInfoConvert.class);

    @Mappings({})
    LeaveInfoDTO convertDOToDTO(LeaveInfoDO leaveInfoDO);

    @Mappings({})
    LeaveInfoDO convertDTOToDO(LeaveInfoDTO leaveInfoDTO);
}
