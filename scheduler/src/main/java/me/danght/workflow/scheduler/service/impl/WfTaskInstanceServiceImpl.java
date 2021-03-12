package me.danght.workflow.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.quarkus.redis.client.RedisClient;
import me.danght.workflow.common.api.schduler.TaskInstanceService;
import me.danght.workflow.scheduler.convert.WfTaskInstanceConvert;
import me.danght.workflow.scheduler.dao.WfActivtityInstanceMapper;
import me.danght.workflow.scheduler.dao.WfProcessInstanceMapper;
import me.danght.workflow.scheduler.dao.WfTaskHistoryInstanceMapper;
import me.danght.workflow.scheduler.dao.WfTaskInstanceMapper;
import me.danght.workflow.scheduler.dataobject.WfTaskHistoryInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfTaskInstanceDO;
import org.apache.dubbo.config.annotation.Service;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
@org.springframework.stereotype.Service
public class WfTaskInstanceServiceImpl implements TaskInstanceService {
    @Autowired
    WfTaskInstanceMapper wfTaskInstanceMapper;

    @Autowired
    WfTaskHistoryInstanceMapper wfTaskHistoryInstanceMapper;

    @Autowired
    WfActivtityInstanceMapper wfActivtityInstanceMapper;

    @Autowired
    WfProcessInstanceMapper wfProcessInstanceMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    RedisClient redisClient;


/*    @Override
    public boolean completeTask(String tiId, Map<String, Object> requiredData) {
        //更新任务状态为已完成
        WfTaskInstanceDO wfTaskInstanceDO = wfTaskInstanceMapper.selectById(tiId);
        wfTaskInstanceDO.setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_COMPLETED);
        //数据转换准备发送给调度器
        WfTaskInstanceMessage wfTaskInstanceMessage = WfTaskInstanceConvert.INSTANCE.convertDOToMessage(wfTaskInstanceDO);
        wfTaskInstanceMessage.setRequiredData(requiredData);
        //sendScheduleRequestMessage(wfTaskInstanceMessage);
        sendMessageInTransaction(wfTaskInstanceMessage,wfTaskInstanceDO);
        return true;
    }*/

    public TransactionSendResult sendMessageInTransaction(WfTaskInstanceMessage wfTaskInstanceMessage,WfTaskInstanceDO wfTaskInstanceDO) {
        // 创建 Demo07Message 消息
        Message<ScheduleRequestMessage> message = MessageBuilder.withPayload(new ScheduleRequestMessage().setWfTaskInstanceMessage(wfTaskInstanceMessage))
                .build();
        // 发送事务消息,最后一个参数事务处理用
        return rocketMQTemplate.sendMessageInTransaction("task-transaction-producer-group", ScheduleRequestMessage.TOPIC, message, WfTaskInstanceConvert.INSTANCE.convertDOToDTO(wfTaskInstanceDO));
    }

    @Transactional
    @Override
    public void ending(WfTaskInstanceDTO wfTaskInstanceDTO){
        //进行历史记录
        WfTaskInstanceDO wfTaskInstanceDO = WfTaskInstanceConvert.INSTANCE.convertDTOToDO(wfTaskInstanceDTO);
        wfTaskInstanceMapper.updateById(wfTaskInstanceDO);
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
        wfTaskHistoryInstanceDO.setCreatetime(new Date());
        wfTaskHistoryInstanceDO.setUpdatetime(wfTaskHistoryInstanceDO.getCreatetime());
        wfTaskHistoryInstanceMapper.insert(wfTaskHistoryInstanceDO);
        //从运行表中清除当前记录
        wfTaskInstanceMapper.deleteById(wfTaskInstanceDO.getId());
    }

    /**
     * 根据状态和aiId进行查询
     * @param wfTaskInstanceQueryDTO
     * @return
     */
    @Override
    public int count(WfTaskInstanceQueryDTO wfTaskInstanceQueryDTO) {
        HashMap<String,String> map = new HashMap<>();
        map.put("ti_status",wfTaskInstanceQueryDTO.getTiStatus());
        map.put("ai_id",wfTaskInstanceQueryDTO.getAiId());
        QueryWrapper<WfTaskInstanceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map);
        return wfTaskInstanceMapper.selectCount(queryWrapper);
    }


    @Override
    public List<WfTaskInstanceDTO> findRelatedTaskList(String aiId) {
        HashMap<String,Object> conditionMap = new HashMap<>();
        conditionMap.put("ai_id",aiId);
        conditionMap.put("ti_status",TaskInstanceState.TASK_INSTANCE_STATE_COMPLETED);
        List<WfTaskInstanceDO> wfTaskInstanceDOList = wfTaskInstanceMapper.selectByMap(conditionMap);
        List<WfTaskInstanceDTO> wfTaskInstanceDTOList = new ArrayList<>();
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            wfTaskInstanceDTOList.add(WfTaskInstanceConvert.INSTANCE.convertDOToDTO(wfTaskInstanceDO));
        }
        return wfTaskInstanceDTOList;
    }

    @Override
    public void moveRelatedTaskToHistory(String aiId) {
        Map<String,Object> conditionMap = new HashMap<>();
        conditionMap.put("ai_id",aiId);
        List<WfTaskInstanceDO> wfTaskInstanceDOList = wfTaskInstanceMapper.selectByMap(conditionMap);
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
            wfTaskHistoryInstanceDO.setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_PAST);
            wfTaskHistoryInstanceDO.setUpdatetime(new Date());
            wfTaskHistoryInstanceDO.setCreatetime(wfTaskHistoryInstanceDO.getUpdatetime());
            wfTaskHistoryInstanceMapper.insert(wfTaskHistoryInstanceDO);
            wfTaskInstanceMapper.deleteById(wfTaskInstanceDO.getId());
        }
    }

    @Override
    public WfTaskInstanceBO save(WfTaskInstanceDTO wfTaskInstanceDTO) {
        WfTaskInstanceDO wfTaskInstanceDO = WfTaskInstanceConvert.INSTANCE.convertDTOToDO(wfTaskInstanceDTO);
        wfTaskInstanceMapper.insert(wfTaskInstanceDO);
        //打时间戳记录
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
        wfTaskHistoryInstanceDO.setCreatetime(new Date());
        wfTaskHistoryInstanceDO.setUpdatetime(wfTaskHistoryInstanceDO.getCreatetime());
        wfTaskHistoryInstanceMapper.insert(wfTaskHistoryInstanceDO);
        return WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceDO);
    }

    @Override
    public void recordHistory(WfTaskInstanceDTO wfTaskInstanceDTO) {
        WfTaskInstanceDO wfTaskInstanceDO = WfTaskInstanceConvert.INSTANCE.convertDTOToDO(wfTaskInstanceDTO);
        WfTaskHistoryInstanceDO wfTaskHistoryInstanceDO = WfTaskInstanceConvert.INSTANCE.convertRunToHistory(wfTaskInstanceDO);
        wfTaskHistoryInstanceDO.setCreatetime(new Date());
        wfTaskHistoryInstanceDO.setUpdatetime(wfTaskHistoryInstanceDO.getCreatetime());
        wfTaskHistoryInstanceMapper.insert(wfTaskHistoryInstanceDO);
    }

    @Override
    public WfTaskInstanceBO getById(String id) {
        return WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceMapper.selectById(id));
    }

    @Override
    public void delete(String id) {
        wfTaskInstanceMapper.deleteById(id);
    }

    @Override
    public String getFirstTaskId(String piId) {
        QueryWrapper<WfTaskInstanceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("pi_id",piId);
        WfTaskInstanceDO wfTaskInstanceDO = wfTaskInstanceMapper.selectOne(queryWrapper);
        return wfTaskInstanceDO.getId();
    }

    @Override
    public WfTaskInstanceDTO updateById(WfTaskInstanceDTO wfTaskInstanceDTO) {
        /*WfActivtityInstanceDO wfActivtityInstanceDO = wfActivtityInstanceMapper.selectById(wfTaskInstanceDTO.getAiId());
        if(wfActivtityInstanceDO.getAiCategory().equals(ActivityInstanceCategory.ACTIVITY_CATEGORY_POSITION)){
            wfActivtityInstanceDO.setAiAssignerType(ActivityInstanceCategory.ACTIVITY_CATEGORY_SINGLE);
            wfActivtityInstanceMapper.updateById(wfActivtityInstanceDO);
            WfTaskInstanceDO wfTaskInstanceDO = WfTaskInstanceConvert.INSTANCE.convertDTOToDO(wfTaskInstanceDTO);
            wfTaskInstanceMapper.updateById(wfTaskInstanceDO);
            return WfTaskInstanceConvert.INSTANCE.convertDOToDTO(wfTaskInstanceDO);
        }*/
        //修改为修改任务执行人种类和执行人
        wfTaskInstanceMapper.updateAssignerType(wfTaskInstanceDTO.getId(),wfTaskInstanceDTO.getTiAssigner());
        //其实还应该回查下是否真的获取到了，高并发情况下有可能被别人抢了，有点懒就没写，而且同一个任务应该不会太多人抢。
        stringRedisTemplate.opsForValue().set(wfTaskInstanceDTO.getId(),"1");
        return wfTaskInstanceDTO;
    }

    @Override
    public List<WfTaskInstanceBO> selectUnCompletedTask(String tiAssigner, String tiAssignerType) {
        QueryWrapper<WfTaskInstanceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ti_assigner",tiAssigner)
                .eq("ti_assigner_type",tiAssignerType);
        List<WfTaskInstanceDO> wfTaskInstanceDOList = wfTaskInstanceMapper.selectList(queryWrapper);
        List<WfTaskInstanceBO> wfTaskInstanceBOList = new ArrayList<>();
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            WfTaskInstanceBO wfTaskInstanceBO = WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceDO);
            WfActivtityInstanceDO wfActivtityInstanceDO = wfActivtityInstanceMapper.selectById(wfTaskInstanceBO.getAiId());
            WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.selectById(wfActivtityInstanceDO.getPiId());
            wfTaskInstanceBO.setPiBusinesskey(wfProcessInstanceDO.getPiBusinesskey());
            wfTaskInstanceBOList.add(wfTaskInstanceBO);
        }
        return wfTaskInstanceBOList;
    }

    @Override
    public List<WfTaskInstanceBO> selectUnObtainedTask(String tiAssigner) {
        QueryWrapper<WfTaskInstanceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ti_assigner",tiAssigner)
                .eq("ti_assigner_type","1");
        List<WfTaskInstanceDO> wfTaskInstanceDOList = wfTaskInstanceMapper.selectList(queryWrapper);
        List<WfTaskInstanceBO> wfTaskInstanceBOList = new ArrayList<>();
        for(WfTaskInstanceDO wfTaskInstanceDO : wfTaskInstanceDOList){
            WfTaskInstanceBO wfTaskInstanceBO = WfTaskInstanceConvert.INSTANCE.convertDOToBO(wfTaskInstanceDO);
            WfActivtityInstanceDO wfActivtityInstanceDO = wfActivtityInstanceMapper.selectById(wfTaskInstanceBO.getAiId());
            WfProcessInstanceDO wfProcessInstanceDO = wfProcessInstanceMapper.selectById(wfActivtityInstanceDO.getPiId());
            wfTaskInstanceBO.setPiBusinesskey(wfProcessInstanceDO.getPiBusinesskey());
            wfTaskInstanceBOList.add(wfTaskInstanceBO);
        }
        return wfTaskInstanceBOList;
    }

    private void sendScheduleRequestMessage(WfTaskInstanceMessage wfTaskInstanceMessage) {
        rocketMQTemplate.convertAndSend(ScheduleRequestMessage.TOPIC, new ScheduleRequestMessage().setWfTaskInstanceMessage(wfTaskInstanceMessage));
    }
}
