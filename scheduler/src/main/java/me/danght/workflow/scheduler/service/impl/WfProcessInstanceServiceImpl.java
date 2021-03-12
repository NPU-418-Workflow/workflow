package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.api.schduler.ProcessInstanceService;
import me.danght.workflow.common.bo.ProcessInstanceBO;
import me.danght.workflow.common.constant.ProcessInstanceState;
import me.danght.workflow.common.dto.ProcessInstanceStartDTO;
import me.danght.workflow.common.msg.ProcessInstanceMessage;
import me.danght.workflow.common.msg.ScheduleRequestMessage;
import me.danght.workflow.scheduler.convert.WfProcessInstanceConvert;
import me.danght.workflow.scheduler.dao.WfProcessHistoryInstanceMapper;
import me.danght.workflow.scheduler.dao.WfProcessInstanceMapper;
import me.danght.workflow.scheduler.dataobject.WfProcessHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfProcessInstanceDO;
import org.apache.dubbo.config.annotation.DubboService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
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
@DubboService(interfaceClass = ProcessInstanceService.class)
@Singleton
public class WfProcessInstanceServiceImpl implements ProcessInstanceService {

    @Inject
    WfProcessInstanceMapper wfProcessInstanceMapper;

    @Inject
    WfProcessHistoryInstanceMapper wfProcessHistoryInstanceMapper;

    @Inject
    @Channel("schedule-request-out")
    Emitter<ScheduleRequestMessage> emitter;

    /**
     * 向数据库wf_process_instance表中插入一条新纪录，之后通过mq向调度器请求调度任务
     * @param wfProcessInstanceStartDTO
     * @return
     */
    @Override
    public ProcessInstanceBO startProcess(ProcessInstanceStartDTO wfProcessInstanceStartDTO) {
        WfProcessInstanceDO wfProcessInstanceDO = WfProcessInstanceConvert.INSTANCE.convertStartDTOToDO(wfProcessInstanceStartDTO);
        wfProcessInstanceDO.setPiStatus(ProcessInstanceState.PROCESS_INSTANCE_STATE_RUNNING);
        wfProcessInstanceDO.setCreatetime(new Date());
        wfProcessInstanceDO.setUpdatetime(wfProcessInstanceDO.getCreatetime());
        wfProcessInstanceMapper.save(wfProcessInstanceDO);
        return WfProcessInstanceConvert.INSTANCE.convertDOToBO(wfProcessInstanceDO);
    }

    /**
     * 终结流程
     * @param piId
     */
    @Override
    public void endProcess(String piId) {
        //修改状态并删除运行实例表中的数据，并在历史表中进行时间戳记录
        WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.findById(piId).get();
        if(wfProcessInstanceDO.getPiStatus().equals(ProcessInstanceState.PROCESS_INSTANCE_STATE_RUNNING))
            wfProcessInstanceDO.setPiStatus(ProcessInstanceState.PROCESS_INSTANCE_STATE_COMPLETED);
        wfProcessInstanceDO.setUpdatetime(new Date());
        wfProcessInstanceDO.setEndtime(wfProcessInstanceDO.getUpdatetime());
        WfProcessHistoryInstanceDO wfProcessHistoryInstanceDO = WfProcessInstanceConvert.INSTANCE.convertRunToHistory(wfProcessInstanceDO);
        wfProcessHistoryInstanceDO.setCreatetime(new Date());
        wfProcessHistoryInstanceDO.setUpdatetime(wfProcessHistoryInstanceDO.getCreatetime());
        wfProcessInstanceMapper.deleteById(piId);
        wfProcessHistoryInstanceMapper.save(wfProcessHistoryInstanceDO);
    }

    @Override
    public void changeProcessState(String piId, String state) {
        WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.findById(piId).get();
        wfProcessInstanceDO.setPiStatus(state);
        wfProcessInstanceMapper.save(wfProcessInstanceDO);
    }

    @Override
    public ProcessInstanceBO getById(String id){
        WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.findById(id).get();
        return WfProcessInstanceConvert.INSTANCE.convertDOToBO(wfProcessInstanceDO);
    }

    @Override
    public List<ProcessInstanceBO> getProcessListByUserId(String piStarter) {
        List<ProcessInstanceBO> wfProcessInstanceBOList = new ArrayList<>();
        List<WfProcessInstanceDO> wfProcessInstanceDOList = (List<WfProcessInstanceDO>) wfProcessInstanceMapper.findAllByPiStarter(piStarter);
        for(WfProcessInstanceDO wfProcessInstanceDO : wfProcessInstanceDOList){
            wfProcessInstanceBOList.add(WfProcessInstanceConvert.INSTANCE.convertDOToBO(wfProcessInstanceDO));
        }
        return wfProcessInstanceBOList;
    }

    private void sendScheduleRequestMessage(ProcessInstanceMessage wfProcessInstanceMessage) {
        emitter.send(new ScheduleRequestMessage().setProcessInstanceMessage(wfProcessInstanceMessage));
    }
}
