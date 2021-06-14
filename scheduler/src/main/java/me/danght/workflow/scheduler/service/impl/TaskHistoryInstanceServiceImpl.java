package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.convert.TaskHistoryInstanceConvert;
import me.danght.workflow.scheduler.dao.TaskHistoryInstanceRepository;
import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dto.TaskHistoryInstanceDTO;
import me.danght.workflow.scheduler.service.TaskHistoryInstanceService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

/**
 * 历史任务服务
 *
 * @author wenxiang
 * @author DangHT
 */
@ApplicationScoped
@ActivateRequestContext
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
