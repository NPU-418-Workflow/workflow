package me.danght.workflow.scheduler.service;

import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.element.BaseElement;
import me.danght.workflow.scheduler.element.StartEvent;

import java.util.List;

public interface ActivityInstanceService {
    ActivityInstanceBO getById(String id);

    ActivityInstanceBO getByPiIdAndUserTaskNo(String piId, String userTaskNo);

    void update(ActivityInstanceDTO activityInstanceDTO);

    void clearActivityOfProcess(String piId);

    List<ActivityInstanceBO> addActivityList(List<BaseElement> userTaskList, String piId, String pdId);

    ActivityInstanceBO addStartEventActivity(StartEvent startEvent, String piId, String pdId, String piStarter);
}
