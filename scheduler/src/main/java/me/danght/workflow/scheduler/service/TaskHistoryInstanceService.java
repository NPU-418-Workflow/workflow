package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.dto.TaskHistoryInstanceDTO;

public interface TaskHistoryInstanceService {
    void save(TaskHistoryInstanceDTO wfTaskHistoryInstanceDTO);

    void delete(String id);
}
