package me.danght.workflow.scheduler.element;

import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.api.schduler.ProcessInstanceService;
import me.danght.workflow.common.constant.ParamType;
import me.danght.workflow.scheduler.bo.ProcessParamsRecordBO;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;
import me.danght.workflow.scheduler.tools.JexlUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 排他网关元素类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ExclusiveGateway extends Gateway implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void execute(Token token,
                        ProcessParamsRecordService processParamsRecordService,
                        TokenRepository tokenRepository,
                        TaskInstanceRepository taskInstanceRepository,
                        ActivityInstanceService activityInstanceService,
                        ProcessInstanceService processInstanceService,
                        RedisClient redisClient){
        /*TODO 这款先暂时还是遍历sf，找第一个符合条件的转移路线，后续想改成在ExclusiveGateway加一个表达式属性，然后直接返回转移sf的no
        这样ExclusiveGateway相连的sf就可以不写判断表达式，减少判断次数，也能比较好的控制排他性*/
        for(SequenceFlow sequenceFlow : outgoingFlows){
            if(sequenceFlow.getConditionExpression() == null){
                //在连接线上没有令牌住所，佩特里网transition不持有令牌
                token.setCurrentNode(null);
                ((Node)sequenceFlow.getTo()).enter(token,
                        processParamsRecordService,
                        tokenRepository,
                        taskInstanceRepository,
                        activityInstanceService,
                        processInstanceService,
                        redisClient);
            }else if(sequenceFlow.getConditionExpression() != null && sequenceFlow.getConditionExpression().length() > 0){
                //TODO 如有条件表达式，则开始条件判断，符合条件才能通过，否则丢弃令牌，条件判断逻辑同排他网关handler部分
                Map<String,Object> requiredData = new HashMap<>();
                for(DataParam dataParam : sequenceFlow.getParamList()){
                    ProcessParamsRecordBO processParamsRecordBO = processParamsRecordService
                            .getByEnginePpName(
                                    dataParam.getEnginePpName(),
                                    token.getPiId(),
                                    token.getPdId(),
                                    dataParam.getTaskNo()
                            );
                    if (processParamsRecordBO != null){
                        switch (processParamsRecordBO.getPpType()){
                            case ParamType.PARAM_TYPE_BOOL:
                                requiredData.put(dataParam.getEnginePpName(), "1".equals(processParamsRecordBO.getPpRecordValue()));
                                break;
                            case ParamType.PARAM_TYPE_INT:
                                requiredData.put(dataParam.getEnginePpName(),Integer.parseInt(processParamsRecordBO.getPpRecordValue()));
                                break;
                            case ParamType.PARAM_TYPE_FLOAT:
                                requiredData.put(dataParam.getEnginePpName(),Float.parseFloat(processParamsRecordBO.getPpRecordValue()));
                                break;
                            case ParamType.PARAM_TYPE_STRING:
                                requiredData.put(dataParam.getEnginePpName(), processParamsRecordBO.getPpRecordValue());
                                break;
                            default:
                                break;
                        }
                    }
                }
                if(JexlUtil.conditionIsMacth(sequenceFlow.getConditionExpression(),requiredData)){
                    token.setCurrentNode(null);
                    token.setElementNo(no);
                    ((Node)sequenceFlow.getTo()).enter(token,
                            processParamsRecordService,
                            tokenRepository,
                            taskInstanceRepository,
                            activityInstanceService,
                            processInstanceService,
                            redisClient);
                    break;
                }
            }
        }
    }
}
