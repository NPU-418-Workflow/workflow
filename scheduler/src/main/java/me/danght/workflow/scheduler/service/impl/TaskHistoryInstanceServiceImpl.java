package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.api.schduler.TaskHistoryInstanceService;
import me.danght.workflow.common.dto.TaskHistoryInstanceDTO;
import me.danght.workflow.scheduler.convert.TaskHistoryInstanceConvert;
import me.danght.workflow.scheduler.dao.TaskHistoryInstanceRepository;
import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.inject.Inject;
import javax.inject.Singleton;

@DubboService(interfaceClass = TaskHistoryInstanceService.class)
@Singleton
public class TaskHistoryInstanceServiceImpl implements TaskHistoryInstanceService {

    @Inject
    TaskHistoryInstanceRepository taskHistoryInstanceRepository;

    @Override
    public void save(TaskHistoryInstanceDTO taskHistoryInstanceDTO) {
        TaskHistoryInstanceDO taskHistoryInstanceDO = TaskHistoryInstanceConvert.INSTANCE.convertDTOToDO(taskHistoryInstanceDTO);
        taskHistoryInstanceRepository.save(taskHistoryInstanceDO);
    }

    @Override
    public void delete(String id) {
        taskHistoryInstanceRepository.deleteById(id);
    }
}
