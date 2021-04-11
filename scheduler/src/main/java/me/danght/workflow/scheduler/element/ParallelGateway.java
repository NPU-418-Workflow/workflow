package me.danght.workflow.scheduler.element;

import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.api.schduler.ProcessInstanceService;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;

import java.util.ArrayList;


/**
 * <p>
 * 并行网关元素类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @author DangHT
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ParallelGateway extends Gateway {
    //分支和聚合网关之间的关联
    //protected ParallelGateway relatedGateWay;
/*    protected String relatedGateWay;*/

    @Override
    public void execute(Token token,
                        ProcessParamsRecordService processParamsRecordService,
                        TokenRepository tokenRepository,
                        TaskInstanceRepository taskInstanceRepository,
                        ActivityInstanceService activityInstanceService,
                        ProcessInstanceService processInstanceService,
                        RedisClient redisClient){
        if(outgoingFlows.size() > 1){
            fork(token, processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
        }else {
            merge(token, processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
        }
    }

    public void fork(Token token,
                     ProcessParamsRecordService processParamsRecordService,
                     TokenRepository tokenRepository,
                     TaskInstanceRepository taskInstanceRepository,
                     ActivityInstanceService activityInstanceService,
                     ProcessInstanceService processInstanceService,
                     RedisClient redisClient){
        token.setChildNum(outgoingFlows.size());
        tokenRepository.save(token);
        for(SequenceFlow sequenceFlow : outgoingFlows){
            Token childToken = new Token();
            childToken.setParent(token);
            childToken.setParentId(token.getId());
            childToken.setCurrentNode(this);
            childToken.setElementNo(no);
            childToken.setPdId(token.getPdId());
            childToken.setPiId(token.getPiId());
            //主要是为了拿Id
            tokenRepository.save(childToken);
            token.getChildren().add(childToken);
            leave(childToken, sequenceFlow, processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
        }
    }

    public void merge(Token token,
                      ProcessParamsRecordService processParamsRecordService,
                      TokenRepository tokenRepository,
                      TaskInstanceRepository taskInstanceRepository,
                      ActivityInstanceService activityInstanceService,
                      ProcessInstanceService processInstanceService,
                      RedisClient redisClient){
        //判断当前token是不是父token的最后一个子token，如果是就把爹弄来，不是就删了自个了事
        tokenRepository.deleteById(token.getId());
        Token parentToken = tokenRepository.findById(token.getParentId()).orElse(null);
        if (parentToken == null) {
            return;
        }
        parentToken.setChildNum(parentToken.getChildNum() - 1);
        tokenRepository.save(parentToken);
        if(parentToken.getChildNum() == 0){
            parentToken.setCurrentNode(this);
            parentToken.setElementNo(this.getNo());
            parentToken.setChildren(new ArrayList<>());
            leave(parentToken, processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
        }
    }

}
