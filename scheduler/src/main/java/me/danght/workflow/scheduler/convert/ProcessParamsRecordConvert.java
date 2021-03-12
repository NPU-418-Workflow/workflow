package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.bo.ProcessParamsRecordBO;
import me.danght.workflow.scheduler.dataobject.ProcessParamsRecordDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProcessParamsRecordConvert {
    ProcessParamsRecordConvert INSTANCE = Mappers.getMapper(ProcessParamsRecordConvert.class);

    @Mappings({})
    ProcessParamsRecordBO convertDOToBO(ProcessParamsRecordDO processParamsRecordDO);
}
