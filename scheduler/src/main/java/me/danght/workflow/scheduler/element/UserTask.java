package me.danght.workflow.scheduler.element;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.constant.TaskInstanceState;
import me.danght.workflow.common.serialization.BaseMapper;
import me.danght.workflow.scheduler.dao.TokenMapper;
import me.danght.workflow.scheduler.dao.WfTaskInstanceMapper;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.dataobject.WfTaskInstanceDO;
import me.danght.workflow.scheduler.service.WfActivtityInstanceService;

import javax.inject.Inject;
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
    /*private ApplicationContext applicationContext =  SpringUtil.getApplicationContext();

    TokenMapper tokenMapper = applicationContext.getBean(TokenMapper.class);

    WfActivtityInstanceService wfActivtityInstanceService = applicationContext.getBean(WfActivtityInstanceService.class);
*/
    //TokenMapper tokenMapper = SpringUtil.getBean(TokenMapper.class);
    //WfActivtityInstanceService wfActivtityInstanceService = SpringUtil.getBean(WfActivtityInstanceService.class);
    //RocketMQTemplate rocketMQTemplate = SpringUtil.getBean(RocketMQTemplate.class);

    //private RocketMQTemplate rocketMQTemplate = applicationContext.getBean(RocketMQTemplate.class);

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

    @Inject
    TokenMapper tokenMapper;

    @Inject
    WfTaskInstanceMapper wfTaskInstanceMapper;

    @Inject
    RedisClient redisClient;

    @Inject
    WfActivtityInstanceService wfActivtityInstanceService;

    @Override
    public void execute(Token token){
        token.setUpdatetime(new Date());
        if(token.getId() == null)
            tokenMapper.save(token);
        else
            tokenMapper.save(token);
        List<BaseElement> readyExecuteUserTaskList = new ArrayList<>();
        readyExecuteUserTaskList.add(this);
        List<ActivityInstanceBO> wfActivityInstanceBOList = wfActivtityInstanceService
                .addActivityList(
                        readyExecuteUserTaskList,
                        token.getPiId(),
                        token.getPdId()
                );
        List<String> assigners = null;
        try {
            assigners = BaseMapper.getObjectMapper().readValue(wfActivityInstanceBOList.get(0).getAiAssignerId(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (assigners != null) pushTask(wfActivityInstanceBOList.get(0),assigners);
    }

    public void pushTask(ActivityInstanceBO wfActivtityInstanceBO,List<String> assigners){
        for(String assignerId : assigners){
            WfTaskInstanceDO wfTaskInstanceDO = new WfTaskInstanceDO()
                    .setTiName(wfActivtityInstanceBO.getAiName())
                    .setTiAssigner(assignerId)
                    .setTiStatus(TaskInstanceState.TASK_INSTANCE_STATE_RUNNING)
                    .setBfId(wfActivtityInstanceBO.getBfId())
                    .setAiId(wfActivtityInstanceBO.getId())
                    .setPdId(wfActivtityInstanceBO.getPdId())
                    .setTiAssignerType(wfActivtityInstanceBO.getAiAssignerType())
                    .setPiId(wfActivtityInstanceBO.getPiId())
                    .setUsertaskNo(wfActivtityInstanceBO.getUserTaskNo());
            wfTaskInstanceDO.setCreatetime(new Date());
            wfTaskInstanceDO.setUpdatetime(wfTaskInstanceDO.getCreatetime());
            wfTaskInstanceMapper.save(wfTaskInstanceDO);
            redisClient.append(wfTaskInstanceDO.getId(),"1");
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
