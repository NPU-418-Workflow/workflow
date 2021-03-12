package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.dto.ProcessTemplateDTO;
import me.danght.workflow.scheduler.dataobject.WfProcessTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WfProcessTemplateConvert {
    WfProcessTemplateConvert INSTANCE = Mappers.getMapper(WfProcessTemplateConvert.class);

    @Mappings({})
    ProcessTemplateDTO convertDOToDTO(WfProcessTemplateDO wfProcessTemplateDO);
}
