package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.TaskHistoryInstanceBO;
import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.dto.TaskInstanceDTO;
import me.danght.workflow.common.msg.TaskInstanceMessage;
import me.danght.workflow.scheduler.dataobject.WfTaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfTaskInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WfTaskInstanceConvert {
    WfTaskInstanceConvert INSTANCE = Mappers.getMapper(WfTaskInstanceConvert.class);

    @Mappings({})
    TaskInstanceMessage convertDOToMessage(WfTaskInstanceDO wfTaskInstanceDO);

    @Mappings({})
    TaskInstanceDTO convertDOToDTO(WfTaskInstanceDO wfTaskInstanceDO);

    @Mappings({})
    TaskInstanceBO convertDOToBO(WfTaskInstanceDO wfTaskInstanceDO);

    @Mappings({})
    WfTaskInstanceDO convertDTOToDO(TaskInstanceDTO wfTaskInstanceDTO);

    @Mappings({
            @Mapping(source = "id", target = "tiId"),
            @Mapping(source = "endtime", target = "tiEndtime"),
            @Mapping(source = "createtime", target = "tiCreatetime"),
            @Mapping(source = "updatetime", target = "tiUpdatetime"),
            @Mapping(target = "id",  ignore = true)
    })
    WfTaskHistoryInstanceDO convertRunToHistory(WfTaskInstanceDO wfTaskInstanceDO);

    @Mappings({
            @Mapping(source = "id", target = "tiId"),
            @Mapping(source = "endtime", target = "tiEndtime"),
            @Mapping(source = "createtime", target = "tiCreatetime"),
            @Mapping(source = "updatetime", target = "tiUpdatetime"),
            @Mapping(target = "id",  ignore = true)
    })
    TaskHistoryInstanceBO convertBOToHistory(TaskInstanceBO wfTaskInstanceBO);
}
