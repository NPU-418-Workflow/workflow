package me.danght.workflow.common.api.schduler;

import me.danght.workflow.common.dto.TaskHistoryInstanceDTO;

public interface TaskHistoryInstanceService {
    void save(TaskHistoryInstanceDTO wfTaskHistoryInstanceDTO);

    void delete(String id);
}
