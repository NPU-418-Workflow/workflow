package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.bo.WfProcessParamsRecordBO;
import me.danght.workflow.scheduler.dataobject.WfProcessParamsRecordDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WfProcessParamsRecordConvert {
    WfProcessParamsRecordConvert INSTANCE = Mappers.getMapper(WfProcessParamsRecordConvert.class);

    @Mappings({})
    WfProcessParamsRecordBO convertDOToBO(WfProcessParamsRecordDO wfProcessParamsRecordDO);
}
