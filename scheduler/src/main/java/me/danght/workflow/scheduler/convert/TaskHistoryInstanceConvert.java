package me.danght.workflow.scheduler.convert;

import me.danght.workflow.scheduler.bo.TaskHistoryInstanceBO;
import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dto.TaskHistoryInstanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface TaskHistoryInstanceConvert {
    TaskHistoryInstanceConvert INSTANCE = Mappers.getMapper(TaskHistoryInstanceConvert.class);
    @Mappings({})
    TaskHistoryInstanceDTO convertDOToDTO(TaskHistoryInstanceDO taskHistoryInstanceDO);

    @Mappings({})
    TaskHistoryInstanceDO convertDTOToDO(TaskHistoryInstanceDTO wfTaskHistoryInstanceDTO);

    @Mappings({})
    TaskHistoryInstanceDTO convertBOToDTO(TaskHistoryInstanceBO wfTaskHistoryInstanceBO);

}
