package me.danght.workflow.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oilpeddler.wfengine.common.api.scheduleservice.WfProcessInstanceService;
import com.oilpeddler.wfengine.common.bo.WfProcessInstanceBO;
import com.oilpeddler.wfengine.common.constant.ProcessInstanceState;
import com.oilpeddler.wfengine.common.dto.WfProcessInstanceStartDTO;
import com.oilpeddler.wfengine.common.message.ScheduleRequestMessage;
import com.oilpeddler.wfengine.common.message.WfProcessInstanceMessage;
import com.oilpeddler.wfengine.schedulecomponent.convert.WfProcessInstanceConvert;
import com.oilpeddler.wfengine.schedulecomponent.dao.WfProcessHistoryInstanceMapper;
import com.oilpeddler.wfengine.schedulecomponent.dao.WfProcessInstanceMapper;
import com.oilpeddler.wfengine.schedulecomponent.dataobject.WfProcessHistoryInstanceDO;
import com.oilpeddler.wfengine.schedulecomponent.dataobject.WfProcessInstanceDO;
import org.apache.dubbo.config.annotation.Service;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 流程管理器开启新流程RPC服务
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-08
 */
@Service
@org.springframework.stereotype.Service
public class WfProcessInstanceServiceImpl implements WfProcessInstanceService {
    @Autowired
    WfProcessInstanceMapper wfProcessInstanceMapper;

    @Autowired
    WfProcessHistoryInstanceMapper wfProcessHistoryInstanceMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 向数据库wf_process_instance表中插入一条新纪录，之后通过mq向调度器请求调度任务
     * @param wfProcessInstanceStartDTO
     * @return
     */
    @Override
    public WfProcessInstanceBO startProcess(WfProcessInstanceStartDTO wfProcessInstanceStartDTO) {
        WfProcessInstanceDO wfProcessInstanceDO = WfProcessInstanceConvert.INSTANCE.convertStartDTOToDO(wfProcessInstanceStartDTO);
        wfProcessInstanceDO.setPiStatus(ProcessInstanceState.PROCESS_INSTANCE_STATE_RUNNING);
        wfProcessInstanceDO.setCreatetime(new Date());
        wfProcessInstanceDO.setUpdatetime(wfProcessInstanceDO.getCreatetime());
        wfProcessInstanceMapper.insert(wfProcessInstanceDO);
        return WfProcessInstanceConvert.INSTANCE.convertDOToBO(wfProcessInstanceDO);
    }

    /**
     * 终结流程
     * @param piId
     */
    @Override
    public void endProcess(String piId) {
        //修改状态并删除运行实例表中的数据，并在历史表中进行时间戳记录
        WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.selectById(piId);
        if(wfProcessInstanceDO.getPiStatus().equals(ProcessInstanceState.PROCESS_INSTANCE_STATE_RUNNING))
            wfProcessInstanceDO.setPiStatus(ProcessInstanceState.PROCESS_INSTANCE_STATE_COMPLETED);
        wfProcessInstanceDO.setUpdatetime(new Date());
        wfProcessInstanceDO.setEndtime(wfProcessInstanceDO.getUpdatetime());
        WfProcessHistoryInstanceDO wfProcessHistoryInstanceDO = WfProcessInstanceConvert.INSTANCE.convertRunToHistory(wfProcessInstanceDO);
        wfProcessHistoryInstanceDO.setCreatetime(new Date());
        wfProcessHistoryInstanceDO.setUpdatetime(wfProcessHistoryInstanceDO.getCreatetime());
        wfProcessInstanceMapper.deleteById(piId);
        wfProcessHistoryInstanceMapper.insert(wfProcessHistoryInstanceDO);
    }

    @Override
    public void changeProcessState(String piId, String state) {
        WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.selectById(piId);
        wfProcessInstanceDO.setPiStatus(state);
        wfProcessInstanceMapper.updateById(wfProcessInstanceDO);
    }

    @Override
    public WfProcessInstanceBO getById(String id){
        WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.selectById(id);
        return WfProcessInstanceConvert.INSTANCE.convertDOToBO(wfProcessInstanceDO);
    }

    @Override
    public List<WfProcessInstanceBO> getProcessListByUserId(String piStarter) {
        QueryWrapper<WfProcessInstanceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pi_starter",piStarter);
        List<WfProcessInstanceBO> wfProcessInstanceBOList = new ArrayList<>();
        List<WfProcessInstanceDO> wfProcessInstanceDOList = wfProcessInstanceMapper.selectList(queryWrapper);
        for(WfProcessInstanceDO wfProcessInstanceDO : wfProcessInstanceDOList){
            wfProcessInstanceBOList.add(WfProcessInstanceConvert.INSTANCE.convertDOToBO(wfProcessInstanceDO));
        }
        return wfProcessInstanceBOList;
    }

    public void haha() {
        System.out.println("haha");
    }


    private void sendScheduleRequestMessage(WfProcessInstanceMessage wfProcessInstanceMessage) {
        rocketMQTemplate.convertAndSend(ScheduleRequestMessage.TOPIC, new ScheduleRequestMessage().setWfProcessInstanceMessage(wfProcessInstanceMessage));
    }
}
