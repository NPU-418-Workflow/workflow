package me.danght.workflow.scheduler.element;

import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.bo.ActivityInstanceBO;
import me.danght.workflow.common.constant.TaskInstanceState;
import me.danght.workflow.scheduler.dao.TokenMapper;
import me.danght.workflow.scheduler.dao.WfProcessInstanceMapper;
import me.danght.workflow.scheduler.dao.WfTaskInstanceMapper;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.dataobject.WfProcessInstanceDO;
import me.danght.workflow.scheduler.dataobject.WfTaskInstanceDO;
import me.danght.workflow.scheduler.service.WfActivtityInstanceService;

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
    WfTaskInstanceMapper wfTaskInstanceMapper;

    @Inject
    TokenMapper tokenMapper;

    @Inject
    RedisClient redisClient;

    @Inject
    WfProcessInstanceMapper wfProcessInstanceMapper;

    @Inject
    WfActivtityInstanceService wfActivtityInstanceService;

    @Override
    public void execute(Token token){
        //开始流程,目前的设计是，只有usertask会持久化token到数据库
        token.setCreatetime(new Date());
        //这块本没必要入库，但是考虑到如果流程一开始就进入fork的情况，需要parentId，所以先入库拿Id
        tokenMapper.save(token);
        WfProcessInstanceDO wfProcessInstanceDO =  wfProcessInstanceMapper.findById(token.getPiId()).get();
        ActivityInstanceBO wfActivtityInstanceBO = wfActivtityInstanceService
                .addStartEventActivity(
                        this,
                        token.getPiId(),
                        token.getPdId(),
                        wfProcessInstanceDO.getPiStarter()
                );
        pushTask(wfActivtityInstanceBO,wfProcessInstanceDO.getPiStarter());
        //SpringUtil.getBean(TokenCacheDao.class).set(token.getId(),token);
        //leave(token);
    }

    public WfTaskInstanceDO pushTask(ActivityInstanceBO wfActivtityInstanceBO, String ass){
        WfTaskInstanceDO wfTaskInstanceDO = new WfTaskInstanceDO()
                .setTiName(wfActivtityInstanceBO.getAiName())
                .setTiAssigner(ass)
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
        redisClient.append(wfTaskInstanceDO.getId(), "1");
        return wfTaskInstanceDO;
    }
}
