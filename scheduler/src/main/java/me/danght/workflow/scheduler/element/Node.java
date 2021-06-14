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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Node extends BaseElement implements Serializable {

    public List<SequenceFlow> incomingFlows = new ArrayList<SequenceFlow>();
    public List<SequenceFlow> outgoingFlows = new ArrayList<SequenceFlow>();

    public void leave(Token token,
                      ProcessParamsRecordService processParamsRecordService,
                      TokenRepository tokenRepository,
                      TaskInstanceRepository taskInstanceRepository,
                      ActivityInstanceService activityInstanceService,
                      ProcessInstanceService processInstanceService,
                      RedisClient redisClient){
        leave(token,getDefaultOutgoing(), processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
    }

    public void leave(Token token,
                      SequenceFlow sequenceFlow,
                      ProcessParamsRecordService processParamsRecordService,
                      TokenRepository tokenRepository,
                      TaskInstanceRepository taskInstanceRepository,
                      ActivityInstanceService activityInstanceService,
                      ProcessInstanceService processInstanceService,
                      RedisClient redisClient){
        token.setCurrentNode(this);
        token.setElementNo(no);
        sequenceFlow.take(token, processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
    }

    public SequenceFlow getDefaultOutgoing(){
        //TODO 先不做判空，但是结束节点怎么解决还没考虑
        if(outgoingFlows == null || outgoingFlows.size() == 0)
            return null;
        return outgoingFlows.get(0);
    }

    public void enter(Token token,
                      ProcessParamsRecordService processParamsRecordService,
                      TokenRepository tokenRepository,
                      TaskInstanceRepository taskInstanceRepository,
                      ActivityInstanceService activityInstanceService,
                      ProcessInstanceService processInstanceService,
                      RedisClient redisClient){
        token.setCurrentNode(this);
        token.setElementNo(no);
        execute(token,
                processParamsRecordService,
                tokenRepository,
                taskInstanceRepository,
                activityInstanceService,
                processInstanceService,
                redisClient);
    }
    public void execute(Token token,
                        ProcessParamsRecordService processParamsRecordService,
                        TokenRepository tokenRepository,
                        TaskInstanceRepository taskInstanceRepository,
                        ActivityInstanceService activityInstanceService,
                        ProcessInstanceService processInstanceService,
                        RedisClient redisClient){
        //这块由子类重写，所以父类方法为空
    }
}
