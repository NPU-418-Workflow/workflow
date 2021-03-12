package me.danght.workflow.common.api.schduler;

import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.dto.TaskInstanceDTO;
import me.danght.workflow.common.dto.TaskInstanceQueryDTO;

import java.util.List;

public interface TaskInstanceService {
    //boolean completeTask(String tiId, Map<String, Object> requiredData);

    void ending(TaskInstanceDTO wfTaskInstanceDTO);

    int count(TaskInstanceQueryDTO wfTaskInstanceQueryDTO);

    List<TaskInstanceDTO> findRelatedTaskList(String aiId);

    void moveRelatedTaskToHistory(String aiId);

    TaskInstanceBO save(TaskInstanceDTO wfTaskInstanceDTO);

    void recordHistory(TaskInstanceDTO wfTaskInstanceDTO);

    TaskInstanceBO getById(String id);

    void delete(String id);

    String getFirstTaskId(String piId);

    TaskInstanceDTO updateById(TaskInstanceDTO wfTaskInstanceDTO);

    List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType);

    List<TaskInstanceBO> selectUnObtainedTask(String tiAssigner);
}
