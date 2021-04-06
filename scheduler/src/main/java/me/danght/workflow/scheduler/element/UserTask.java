package me.danght.workflow.scheduler.element;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.api.schduler.ProcessInstanceService;
import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.constant.TaskInstanceState;
import me.danght.workflow.common.serialization.BaseMapper;
import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 活动要素类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserTask extends Node {

    /**
     * 活动名称
     */
    protected String name;

    /**
     * 活动参与人类型
     */
    protected String assigneeType;

    /**
     * 活动执行人
     */
    protected List<String> assignees = new ArrayList<>();

    /**
     * 活动类型
     */
    protected String taskType;

    /**
     * 即席活动执行人
     */
    protected String dynamicAssignees;

    /**
     * 即席活动参与人类型
     */
    protected String dynamicAssigneeType;

    /**
     * 挂接表单页面
     */
    protected String pageKey;

    protected List<DataParam> paramList;

    @Override
    public void execute(Token token,
                        ProcessParamsRecordService processParamsRecordService,
                        TokenRepository tokenRepository,
                        TaskInstanceRepository taskInstanceRepository,
                        ActivityInstanceService activityInstanceService,
                        ProcessInstanceService processInstanceService,
                        RedisClient redisClient){
        token.setUpdateTime(new Date());
        tokenRepository.save(token);
        List<BaseElement> readyExecuteUserTaskList = new ArrayList<>();
        readyExecuteUserTaskList.add(this);
        List<ActivityInstanceBO> activityInstanceBOList = activityInstanceService
                .addActivityList(
                        readyExecuteUserTaskList,
                        token.getPiId(),
                        token.getPdId()
                );
        List<String> assigners = null;
        try {
            assigners = BaseMapper.getObjectMapper().readValue(activityInstanceBOList.get(0).getAiAssignerId(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (assigners != null) {
            pushTask(activityInstanceBOList.get(0), assigners, taskInstanceRepository, redisClient);
        }
    }

    public void pushTask(
            ActivityInstanceBO activityInstanceBO,
            List<String> assigners,
            TaskInstanceRepository taskInstanceRepository,
            RedisClient redisClient){
        for(String assignerId : assigners){
            TaskInstanceDO taskInstanceDO = new TaskInstanceDO()
                    .setTiName(activityInstanceBO.getAiName())
                    .setTiAssigner(assignerId)
                    .setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_RUNNING)
                    .setBfId(activityInstanceBO.getBfId())
                    .setAiId(activityInstanceBO.getId())
                    .setPdId(activityInstanceBO.getPdId())
                    .setTiAssignerType(activityInstanceBO.getAiAssignerType())
                    .setPiId(activityInstanceBO.getPiId())
                    .setUserTaskNo(activityInstanceBO.getUserTaskNo());
            taskInstanceDO.setCreateTime(new Date());
            taskInstanceDO.setUpdateTime(taskInstanceDO.getCreateTime());
            taskInstanceRepository.save(taskInstanceDO);
            redisClient.append(taskInstanceDO.getId(),"1");
        }
    }

    /*private void sendTaskRequestMessage(List<WfActivtityInstanceBO> wfActivtityInstanceBOList) {
        SpringUtil.getBean(RocketMQTemplate.class).convertAndSend(TaskRequestMessage.TOPIC, new TaskRequestMessage().setWfActivtityInstanceBOList(wfActivtityInstanceBOList));
    }*/

    /*//TODO 调度器这事务消息有点问题，先停在这
    public TransactionSendResult sendMessageInTransaction(List<WfActivtityInstanceBO> wfActivtityInstanceBOList) {
        // 创建 Demo07Message 消息
        Message<TaskRequestMessage> message = MessageBuilder.withPayload(new TaskRequestMessage().setWfActivtityInstanceBOList(wfActivtityInstanceBOList))
                .build();
        // 发送事务消息,最后一个参数乱写的
        return SpringUtil.getBean(RocketMQTemplate.class).sendMessageInTransaction("schedule-transaction-producer-group", TaskRequestMessage.TOPIC, message, wfActivtityInstanceBOList);
    }*/
    /*protected List<SequenceFlow> incomingFlows = new ArrayList<SequenceFlow>();
    protected List<SequenceFlow> outgoingFlows = new ArrayList<SequenceFlow>();*/

}
