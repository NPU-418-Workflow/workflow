package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.bo.ProcessInstanceBO;
import me.danght.workflow.scheduler.constant.ProcessInstanceState;
import me.danght.workflow.scheduler.convert.ProcessInstanceConvert;
import me.danght.workflow.scheduler.dao.ProcessHistoryInstanceRepository;
import me.danght.workflow.scheduler.dao.ProcessInstanceRepository;
import me.danght.workflow.scheduler.dataobject.ProcessHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.ProcessInstanceDO;
import me.danght.workflow.scheduler.dto.ProcessInstanceStartDTO;
import me.danght.workflow.scheduler.msg.ProcessInstanceMessage;
import me.danght.workflow.scheduler.msg.ScheduleRequestMessage;
import me.danght.workflow.scheduler.service.ProcessInstanceService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 流程实例服务
 * 流程管理器开启新流程RPC服务
 *
 * @author wenxiang
 * @author DangHT
 * @since 2019-10-08
 */
@ApplicationScoped
@ActivateRequestContext
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Inject
    ProcessInstanceRepository processInstanceRepository;

    @Inject
    ProcessHistoryInstanceRepository processHistoryInstanceRepository;

    @Inject
    @Channel("schedule-request-out")
    Emitter<ScheduleRequestMessage> emitter;

    /**
     * 向数据库wf_process_instance表中插入一条新纪录，之后通过mq向调度器请求调度任务
     * @param processInstanceStartDTO
     * @return
     */
    @Override
    public ProcessInstanceBO startProcess(ProcessInstanceStartDTO processInstanceStartDTO) {
        ProcessInstanceDO processInstanceDO = ProcessInstanceConvert.INSTANCE.convertStartDTOToDO(processInstanceStartDTO);
        processInstanceDO.setPiStatus(ProcessInstanceState.PROCESS_INSTANCE_STATE_RUNNING);
        processInstanceDO.setCreateTime(new Date());
        processInstanceDO.setUpdateTime(processInstanceDO.getCreateTime());
        processInstanceRepository.save(processInstanceDO);
        return ProcessInstanceConvert.INSTANCE.convertDOToBO(processInstanceDO);
    }

    /**
     * 终结流程
     * @param piId
     */
    @Override
    public void endProcess(String piId) {
        //修改状态并删除运行实例表中的数据，并在历史表中进行时间戳记录
        ProcessInstanceDO processInstanceDO = processInstanceRepository.findById(piId).orElse(null);
        if (processInstanceDO == null) return;
        if(processInstanceDO.getPiStatus().equals(ProcessInstanceState.PROCESS_INSTANCE_STATE_RUNNING))
            processInstanceDO.setPiStatus(ProcessInstanceState.PROCESS_INSTANCE_STATE_COMPLETED);
        processInstanceDO.setUpdateTime(new Date());
        processInstanceDO.setEndTime(processInstanceDO.getUpdateTime());
        ProcessHistoryInstanceDO processHistoryInstanceDO = ProcessInstanceConvert.INSTANCE.convertRunToHistory(processInstanceDO);
        processHistoryInstanceDO.setCreateTime(new Date());
        processHistoryInstanceDO.setUpdateTime(processHistoryInstanceDO.getCreateTime());
        processInstanceRepository.deleteById(piId);
        processHistoryInstanceRepository.save(processHistoryInstanceDO);
    }

    @Override
    public void changeProcessState(String piId, String state) {
        ProcessInstanceDO processInstanceDO = processInstanceRepository.findById(piId).orElse(null);
        if (processInstanceDO == null) return;
        processInstanceDO.setPiStatus(state);
        processInstanceRepository.save(processInstanceDO);
    }

    @Override
    public ProcessInstanceBO getById(String id){
        ProcessInstanceDO processInstanceDO = processInstanceRepository.findById(id).get();
        return ProcessInstanceConvert.INSTANCE.convertDOToBO(processInstanceDO);
    }

    @Override
    public List<ProcessInstanceBO> getProcessListByUserId(String piStarter) {
        List<ProcessInstanceBO> processInstanceBOList = new ArrayList<>();
        List<ProcessInstanceDO> processInstanceDOList = processInstanceRepository.findAllByPiStarter(piStarter);
        for(ProcessInstanceDO processInstanceDO : processInstanceDOList){
            processInstanceBOList.add(ProcessInstanceConvert.INSTANCE.convertDOToBO(processInstanceDO));
        }
        return processInstanceBOList;
    }

    private void sendScheduleRequestMessage(ProcessInstanceMessage processInstanceMessage) {
        emitter.send(new ScheduleRequestMessage().setProcessInstanceMessage(processInstanceMessage));
    }
}
