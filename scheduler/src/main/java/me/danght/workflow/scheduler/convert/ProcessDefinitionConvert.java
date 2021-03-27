package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.ProcessDefinitionBO;
import me.danght.workflow.scheduler.dataobject.ProcessDefinitionDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ProcessDefinitionConvert {
    ProcessDefinitionConvert INSTANCE = Mappers.getMapper(ProcessDefinitionConvert.class);

    @Mappings({})
    ProcessDefinitionBO convertDOToBO(ProcessDefinitionDO processDefinitionDO);
}
