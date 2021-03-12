package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.ProcessDefinitionBO;
import me.danght.workflow.scheduler.dataobject.WfProcessDefinitionDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WfProcessDefinitionConvert {
    WfProcessDefinitionConvert INSTANCE = Mappers.getMapper(WfProcessDefinitionConvert.class);

    @Mappings({})
    ProcessDefinitionBO convertDOToBO(WfProcessDefinitionDO wfProcessDefinitionDO);
}
