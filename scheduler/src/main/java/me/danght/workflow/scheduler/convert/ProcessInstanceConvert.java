package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.ProcessInstanceBO;
import me.danght.workflow.common.dto.ProcessInstanceStartDTO;
import me.danght.workflow.common.msg.ProcessInstanceMessage;
import me.danght.workflow.scheduler.dataobject.ProcessHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.ProcessInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProcessInstanceConvert {

    ProcessInstanceConvert INSTANCE = Mappers.getMapper(ProcessInstanceConvert.class);

    @Mappings({})
    ProcessInstanceDO convertStartDTOToDO(ProcessInstanceStartDTO wfProcessInstanceStartDTO);

    @Mappings({})
    ProcessInstanceStartDTO convertMessageToStartDTO(ProcessInstanceMessage wfProcessInstanceMessage);

    @Mappings({})
    ProcessInstanceMessage convertDOToMessage(ProcessInstanceDO processInstanceDO);

    @Mappings({
            @Mapping(source = "id", target = "piId"),
            @Mapping(source = "endTime", target = "piEndTime"),
            @Mapping(source = "createTime", target = "piCreateTime"),
            @Mapping(source = "updateTime", target = "piUpdateTime"),
            @Mapping(target = "id",  ignore = true)
    })
    ProcessHistoryInstanceDO convertRunToHistory(ProcessInstanceDO processInstanceDO);

    @Mappings({})
    ProcessInstanceBO convertDOToBO(ProcessInstanceDO processInstanceDO);


}
