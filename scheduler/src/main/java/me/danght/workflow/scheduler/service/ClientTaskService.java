package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.bo.TaskInstanceBO;
import me.danght.workflow.scheduler.dto.TaskInstanceDTO;

import java.util.List;
import java.util.Map;

public interface ClientTaskService {
    boolean completeTask(String tiId, String pdId,String taskNo,Map<String, Object> requiredData);

    List<TaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType);

    TaskInstanceDTO obtainTask(String id, String tiAssigner);

    List<TaskInstanceBO> selectUnObtainTask(String tiAssigner);
}
