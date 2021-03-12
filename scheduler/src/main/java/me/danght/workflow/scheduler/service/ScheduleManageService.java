package me.danght.workflow.scheduler.service;

import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.element.Process;

public interface ScheduleManageService {
/*
    List<BaseElement> getFirstActivity(Process process, String processInstanceId, String pdId);

    //返回值为空代表当前还有关联任务未完成，返回值为endevent说明要结束流程
    List<BaseElement> getNextSteps(UserTask currentUserTask, Process process, String processInstanceId, String pdId);
*/

    UserTask findUserTaskByNo(String no, Process process);
    //boolean IsRelatedUserTaskCompleted()

    void recordActivityHistory(ActivityInstanceDTO wfActivtityInstanceDTO);

}
