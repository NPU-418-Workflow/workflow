package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.ProcessInstanceBO;
import me.danght.workflow.common.dto.ProcessInstanceStartDTO;
import me.danght.workflow.common.msg.ProcessInstanceMessage;
import me.danght.workflow.scheduler.dataobject.WfProcessHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfProcessInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WfProcessInstanceConvert {

    WfProcessInstanceConvert INSTANCE = Mappers.getMapper(WfProcessInstanceConvert.class);

    @Mappings({})
    WfProcessInstanceDO convertStartDTOToDO(ProcessInstanceStartDTO wfProcessInstanceStartDTO);

    @Mappings({})
    ProcessInstanceStartDTO convertMessageToStartDTO(ProcessInstanceMessage wfProcessInstanceMessage);

    @Mappings({})
    ProcessInstanceMessage convertDOToMessage(WfProcessInstanceDO wfProcessInstanceDO);

    @Mappings({
            @Mapping(source = "id", target = "piId"),
            @Mapping(source = "endtime", target = "piEndtime"),
            @Mapping(source = "createtime", target = "piCreatetime"),
            @Mapping(source = "updatetime", target = "piUpdatetime"),
            @Mapping(target = "id",  ignore = true)
    })
    WfProcessHistoryInstanceDO convertRunToHistory(WfProcessInstanceDO wfProcessInstanceDO);

    @Mappings({})
    ProcessInstanceBO convertDOToBO(WfProcessInstanceDO wfProcessInstanceDO);


}
