package me.danght.workflow.common.api.form;

import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.dto.TaskInstanceDTO;

import java.util.List;
import java.util.Map;

public interface ClientTaskService {
    boolean completeTask(String tiId, String pdId,String taskNo,Map<String, Object> requiredData);

    List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType);

    TaskInstanceDTO obtainTask(String id, String tiAssigner);

    List<TaskInstanceBO> selectUnObtainTask(String tiAssigner);
}
