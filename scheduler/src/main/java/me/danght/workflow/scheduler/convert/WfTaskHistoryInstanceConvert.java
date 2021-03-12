package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.TaskHistoryInstanceBO;
import me.danght.workflow.common.dto.TaskHistoryInstanceDTO;
import me.danght.workflow.scheduler.dataobject.WfTaskHistoryInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WfTaskHistoryInstanceConvert {
    WfTaskHistoryInstanceConvert INSTANCE = Mappers.getMapper(WfTaskHistoryInstanceConvert.class);
    @Mappings({})
    TaskHistoryInstanceDTO convertDOToDTO(WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO);

    @Mappings({})
    WfTaskHistoryInstanceDO convertDTOToDO(TaskHistoryInstanceDTO wfTaskHistoryInstanceDTO);

    @Mappings({})
    TaskHistoryInstanceDTO convertBOToDTO(TaskHistoryInstanceBO wfTaskHistoryInstanceBO);

}
