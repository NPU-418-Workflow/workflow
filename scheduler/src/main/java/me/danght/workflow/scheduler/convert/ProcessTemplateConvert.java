package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.dataobject.ProcessTemplateDO;
import me.danght.workflow.scheduler.dto.ProcessTemplateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ProcessTemplateConvert {
    ProcessTemplateConvert INSTANCE = Mappers.getMapper(ProcessTemplateConvert.class);

    @Mappings({})
    ProcessTemplateDTO convertDOToDTO(ProcessTemplateDO processTemplateDO);
}
