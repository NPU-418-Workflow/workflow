package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.dataobject.LeaveInfoDO;
import me.danght.workflow.scheduler.dto.LeaveInfoDTO;
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
