package me.danght.workflow.scheduler.element;

import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * <p>
 * 无指定结束事件类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class EndEvent extends Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    TokenRepository tokenRepository;

    @Inject
    ActivityInstanceService activityInstanceService;

    @Inject
    ProcessInstanceService processInstanceService;

    @Override
    public void execute(
            Token token,
            ProcessParamsRecordService processParamsRecordService,
            TokenRepository tokenRepository,
            TaskInstanceRepository taskInstanceRepository,
            ActivityInstanceService activityInstanceService,
            ProcessInstanceService processInstanceService,
            RedisClient redisClient){
        //结束流程
        tokenRepository.deleteById(token.getId());
        activityInstanceService.clearActivityOfProcess(token.getPiId());
        processInstanceService.endProcess(token.getPiId());
    }
}
