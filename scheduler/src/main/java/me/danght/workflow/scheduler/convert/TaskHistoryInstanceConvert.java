package me.danght.workflow.scheduler.convert;

import me.danght.workflow.common.bo.TaskHistoryInstanceBO;
import me.danght.workflow.common.dto.TaskHistoryInstanceDTO;
import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskHistoryInstanceConvert {
    TaskHistoryInstanceConvert INSTANCE = Mappers.getMapper(TaskHistoryInstanceConvert.class);
    @Mappings({})
    TaskHistoryInstanceDTO convertDOToDTO(TaskHistoryInstanceDO taskHistoryInstanceDO);

    @Mappings({})
    TaskHistoryInstanceDO convertDTOToDO(TaskHistoryInstanceDTO wfTaskHistoryInstanceDTO);

    @Mappings({})
    TaskHistoryInstanceDTO convertBOToDTO(TaskHistoryInstanceBO wfTaskHistoryInstanceBO);

}
