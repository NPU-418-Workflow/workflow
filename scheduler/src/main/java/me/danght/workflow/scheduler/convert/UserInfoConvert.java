package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.dataobject.UserInfoDO;
import me.danght.workflow.scheduler.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface UserInfoConvert {
    UserInfoConvert INSTANCE = Mappers.getMapper(UserInfoConvert.class);

    @Mappings({})
    UserInfoDTO convertDOToDTO(UserInfoDO userInfoDO);

    @Mappings({})
    UserInfoDO convertDTOToDO(UserInfoDTO userInfoDTO);
}
