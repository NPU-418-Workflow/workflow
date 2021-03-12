package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.constant.ParamType;
import me.danght.workflow.scheduler.bo.ProcessParamsRecordBO;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;
import me.danght.workflow.scheduler.tools.JexlUtil;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 顺序流类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SequenceFlow extends BaseElement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 顺序流名称
     */
    protected String name;

    /**
     * 顺序流条件表达式
     */
    protected String conditionExpression = null;

    /**
     * 顺序流起始节点
     */
    protected String sourceRef;

    /**
     * 顺序流目标节点
     */
    protected String targetRef;

    protected BaseElement from = null;
    protected BaseElement to = null;
    protected List<DataParam> paramList;

    @Inject
    ProcessParamsRecordService processParamsRecordService;

    public void take(Token token){
        //如sf不带有条件表达式，则无脑往目标走
        if(conditionExpression == null){
            //在连接线上没有令牌住所，佩特里网transition不持有令牌
            token.setCurrentNode(null);
            ((Node)to).enter(token);
        } else if (conditionExpression.length() > 0){
            //TODO 这块其实用不上，因为无论是排他网关还是选择网关都会提前判定执行那些有向弧进行变迁
            Map<String,Object> requiredData = new HashMap<>();
            for(DataParam dataParam : paramList){
                ProcessParamsRecordBO processParamsRecordBO = processParamsRecordService
                        .getByEnginePpName(
                                dataParam.getEnginePpName(),
                                token.getPiId(),
                                token.getPdId(),
                                dataParam.getTaskNo()
                        );
                if (processParamsRecordBO != null) {
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
            if(JexlUtil.conditionIsMacth(conditionExpression,requiredData)){
                token.setCurrentNode(null);
                token.setElementNo(no);
                ((Node)to).enter(token);
            }
        }
    }
}
