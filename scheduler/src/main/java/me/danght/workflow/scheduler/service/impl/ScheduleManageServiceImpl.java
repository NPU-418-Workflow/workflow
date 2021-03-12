package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.dto.ActivityInstanceDTO;
import me.danght.workflow.scheduler.convert.ActivityInstanceConvert;
import me.danght.workflow.scheduler.dao.ActivityHistoryInstanceRepository;
import me.danght.workflow.scheduler.dataobject.ActivityHistoryInstanceDO;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.service.ScheduleManageService;
import me.danght.workflow.scheduler.element.Process;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class ScheduleManageServiceImpl implements ScheduleManageService {

    @Inject
    ActivityHistoryInstanceRepository activityHistoryInstanceRepository;

    @Override
    public UserTask findUserTaskByNo(String no, Process process) {
        for(UserTask userTask : process.getUserTaskList()){
            if(userTask.getNo().equals(no))
                return userTask;
        }
        return null;
    }

    @Override
    public void recordActivityHistory(ActivityInstanceDTO activityInstanceDTO) {
        ActivityHistoryInstanceDO activityHistoryInstanceDO = ActivityInstanceConvert.INSTANCE.convertRunToHistoryDO(activityInstanceDTO);
        activityHistoryInstanceDO.setCreateTime(new Date());
        activityHistoryInstanceDO.setUpdateTime(activityHistoryInstanceDO.getCreateTime());
        activityHistoryInstanceRepository.save(activityHistoryInstanceDO);
    }


}
