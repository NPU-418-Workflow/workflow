package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.api.schduler.TaskHistoryInstanceService;
import me.danght.workflow.common.dto.TaskHistoryInstanceDTO;
import me.danght.workflow.scheduler.convert.WfTaskHistoryInstanceConvert;
import me.danght.workflow.scheduler.dao.WfTaskHistoryInstanceMapper;
import me.danght.workflow.scheduler.dataobject.WfTaskHistoryInstanceDO;
import org.apache.dubbo.config.annotation.DubboService;

import javax.inject.Inject;
import javax.inject.Singleton;

@DubboService(interfaceClass = TaskHistoryInstanceService.class)
@Singleton
public class WfTaskHistoryInstanceServiceImpl implements TaskHistoryInstanceService {

    @Inject
    WfTaskHistoryInstanceMapper wfTaskHistoryInstanceMapper;

    @Override
    public void save(TaskHistoryInstanceDTO wfTaskHistoryInstanceDTO) {
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskHistoryInstanceConvert.INSTANCE.convertDTOToDO(wfTaskHistoryInstanceDTO);
        wfTaskHistoryInstanceMapper.save(wfTaskHistoryInstanceDO);
    }

    @Override
    public void delete(String id) {
        wfTaskHistoryInstanceMapper.deleteById(id);
    }
}
