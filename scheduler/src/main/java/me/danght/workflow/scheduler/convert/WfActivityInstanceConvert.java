package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.dataobject.WfActivityHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfActivityInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WfActivityInstanceConvert {
    WfActivityInstanceConvert INSTANCE = Mappers.getMapper(WfActivityInstanceConvert.class);

    @Mappings({})
    ActivityInstanceDTO convertDOToDTO(WfActivityInstanceDO wfActivityInstanceDO);

    @Mappings({})
    ActivityInstanceBO convertDOToBO(WfActivityInstanceDO wfActivityInstanceDO);

    @Mappings({})
    ActivityInstanceDTO convertBOToDTO(ActivityInstanceBO wfActivityInstanceBO);

    @Mappings({})
    WfActivityInstanceDO convertDTOToDO(ActivityInstanceDTO wfActivityInstanceDTO);

    @Mappings({
            @Mapping(source = "id", target = "actId"),
            @Mapping(source = "createtime", target = "aiCreatetime"),
            @Mapping(source = "updatetime", target = "aiUpdatetime"),
            @Mapping(target = "id",  ignore = true)
    })
    WfActivityHistoryInstanceDO convertRunToHistoryDO(ActivityInstanceDTO wfActivityInstanceDTO);

    @Mappings({
            @Mapping(source = "id", target = "actId"),
            @Mapping(source = "createtime", target = "aiCreatetime"),
            @Mapping(source = "updatetime", target = "aiUpdatetime"),
            @Mapping(target = "id",  ignore = true)
    })
    WfActivityHistoryInstanceDO convertRunDOToHistoryDO(WfActivityInstanceDO wfActivityInstanceDO);
}
