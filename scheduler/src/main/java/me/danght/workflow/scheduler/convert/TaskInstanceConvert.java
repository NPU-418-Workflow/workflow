package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.TaskHistoryInstanceBO;
import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.dto.TaskInstanceDTO;
import me.danght.workflow.common.msg.TaskInstanceMessage;
import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskInstanceConvert {
    TaskInstanceConvert INSTANCE = Mappers.getMapper(TaskInstanceConvert.class);

    @Mappings({})
    TaskInstanceMessage convertDOToMessage(TaskInstanceDO taskInstanceDO);

    @Mappings({})
    TaskInstanceDTO convertDOToDTO(TaskInstanceDO taskInstanceDO);

    @Mappings({})
    TaskInstanceBO convertDOToBO(TaskInstanceDO taskInstanceDO);

    @Mappings({})
    TaskInstanceDO convertDTOToDO(TaskInstanceDTO taskInstanceDTO);

    @Mappings({
            @Mapping(source = "id", target = "tiId"),
            @Mapping(source = "endTime", target = "tiEndTime"),
            @Mapping(source = "createTime", target = "tiCreateTime"),
            @Mapping(source = "updateTime", target = "tiUpdateTime"),
            @Mapping(target = "id",  ignore = true)
    })
    TaskHistoryInstanceDO convertRunToHistory(TaskInstanceDO taskInstanceDO);

    @Mappings({
            @Mapping(source = "id", target = "tiId"),
            @Mapping(source = "endTime", target = "tiEndTime"),
            @Mapping(source = "createTime", target = "tiCreateTime"),
            @Mapping(source = "updateTime", target = "tiUpdateTime"),
            @Mapping(target = "id",  ignore = true)
    })
    TaskHistoryInstanceBO convertBOToHistory(TaskInstanceBO taskInstanceBO);
}
