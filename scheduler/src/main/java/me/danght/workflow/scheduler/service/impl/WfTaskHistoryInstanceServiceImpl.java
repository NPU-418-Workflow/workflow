package me.danght.workflow.scheduler.service.impl;

import com.oilpeddler.wfengine.common.api.scheduleservice.WfTaskHistoryInstanceService;
import com.oilpeddler.wfengine.common.dto.WfTaskHistoryInstanceDTO;
import com.oilpeddler.wfengine.schedulecomponent.convert.WfTaskHistoryInstanceConvert;
import com.oilpeddler.wfengine.schedulecomponent.dao.WfTaskHistoryInstanceMapper;
import com.oilpeddler.wfengine.schedulecomponent.dataobject.WfTaskHistoryInstanceDO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
@Service
public class WfTaskHistoryInstanceServiceImpl implements WfTaskHistoryInstanceService {

    @Autowired
    WfTaskHistoryInstanceMapper wfTaskHistoryInstanceMapper;

    @Override
    public void save(WfTaskHistoryInstanceDTO wfTaskHistoryInstanceDTO) {
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskHistoryInstanceConvert.INSTANCE.convertDTOToDO(wfTaskHistoryInstanceDTO);
        wfTaskHistoryInstanceMapper.insert(wfTaskHistoryInstanceDO);
    }

    @Override
    public void delete(String id) {
        wfTaskHistoryInstanceMapper.deleteById(id);
    }
}
