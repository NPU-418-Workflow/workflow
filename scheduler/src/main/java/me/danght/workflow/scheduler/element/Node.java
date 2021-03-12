package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.scheduler.dataobject.Token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Node extends BaseElement implements Serializable {

    public List<SequenceFlow> incomingFlows = new ArrayList<SequenceFlow>();
    public List<SequenceFlow> outgoingFlows = new ArrayList<SequenceFlow>();
    public void leave(Token token){
        leave(token,getDefaultOutgoing());
    }
    public void leave(Token token, SequenceFlow sequenceFlow){
        token.setCurrentNode(this);
        token.setElementNo(no);
        sequenceFlow.take(token);
    }

    public SequenceFlow getDefaultOutgoing(){
        //TODO 先不做判空，但是结束节点怎么解决还没考虑
        if(outgoingFlows == null || outgoingFlows.size() == 0)
            return null;
        return outgoingFlows.get(0);
    }

    public void enter(Token token){
        token.setCurrentNode(this);
        token.setElementNo(no);
        execute(token);
    }
    public void execute(Token token){
        //这块由子类重写，所以父类方法为空
    }
}
