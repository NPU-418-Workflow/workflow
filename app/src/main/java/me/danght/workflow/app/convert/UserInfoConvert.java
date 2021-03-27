package me.danght.workflow.app.convert;

import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.UserInfoDTO;
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
