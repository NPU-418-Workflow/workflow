package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.dataobject.ActivityHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.ActivityInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActivityInstanceConvert {
    ActivityInstanceConvert INSTANCE = Mappers.getMapper(ActivityInstanceConvert.class);

    @Mappings({})
    ActivityInstanceDTO convertDOToDTO(ActivityInstanceDO activityInstanceDO);

    @Mappings({})
    ActivityInstanceBO convertDOToBO(ActivityInstanceDO activityInstanceDO);

    @Mappings({})
    ActivityInstanceDTO convertBOToDTO(ActivityInstanceBO activityInstanceBO);

    @Mappings({})
    ActivityInstanceDO convertDTOToDO(ActivityInstanceDTO activityInstanceDTO);

    @Mappings({
            @Mapping(source = "id", target = "actId"),
            @Mapping(source = "createTime", target = "aiCreateTime"),
            @Mapping(source = "updateTime", target = "aiUpdateTime"),
            @Mapping(target = "id",  ignore = true)
    })
    ActivityHistoryInstanceDO convertRunToHistoryDO(ActivityInstanceDTO activityInstanceDTO);

    @Mappings({
            @Mapping(source = "id", target = "actId"),
            @Mapping(source = "createTime", target = "aiCreateTime"),
            @Mapping(source = "updateTime", target = "aiUpdateTime"),
            @Mapping(target = "id",  ignore = true)
    })
    ActivityHistoryInstanceDO convertRunDOToHistoryDO(ActivityInstanceDO activityInstanceDO);
}
