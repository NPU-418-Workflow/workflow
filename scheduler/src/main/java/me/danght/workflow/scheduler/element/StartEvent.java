package me.danght.workflow.scheduler.element;

import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.constant.TaskInstanceState;
import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.dao.ProcessInstanceRepository;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.dataobject.ProcessInstanceDO;
import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import me.danght.workflow.scheduler.service.ActivityInstanceService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 无指定开始事件类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-12-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class StartEvent extends Event {

    /**
     * 挂接表单页面
     */
    protected String pageKey;

    protected List<DataParam> paramList;

    @Inject
    TaskInstanceRepository taskInstanceRepository;

    @Inject
    TokenRepository tokenRepository;

    @Inject
    RedisClient redisClient;

    @Inject
    ProcessInstanceRepository processInstanceRepository;

    @Inject
    ActivityInstanceService activityInstanceService;

    @Override
    public void execute(Token token){
        //开始流程,目前的设计是，只有usertask会持久化token到数据库
        token.setCreateTime(new Date());
        //这块本没必要入库，但是考虑到如果流程一开始就进入fork的情况，需要parentId，所以先入库拿Id
        tokenRepository.save(token);
        ProcessInstanceDO processInstanceDO =  processInstanceRepository.findById(token.getPiId()).orElse(null);
        if (processInstanceDO != null) {
            ActivityInstanceBO activityInstanceBO = activityInstanceService
                    .addStartEventActivity(
                            this,
                            token.getPiId(),
                            token.getPdId(),
                            processInstanceDO.getPiStarter()
                    );
            pushTask(activityInstanceBO, processInstanceDO.getPiStarter());
        }
    }

    public TaskInstanceDO pushTask(ActivityInstanceBO activityInstanceBO, String ass){
        TaskInstanceDO taskInstanceDO = new TaskInstanceDO()
                .setTiName(activityInstanceBO.getAiName())
                .setTiAssigner(ass)
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
        redisClient.append(taskInstanceDO.getId(), "1");
        return taskInstanceDO;
    }
}
