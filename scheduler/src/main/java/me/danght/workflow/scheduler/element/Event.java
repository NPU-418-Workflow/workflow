package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 时间抽象基类
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class Event extends Node {
    /**
     * 事件名称
     */
    protected String name;


    /*protected List<SequenceFlow> incomingFlows = new ArrayList<SequenceFlow>();
    protected List<SequenceFlow> outgoingFlows = new ArrayList<SequenceFlow>();*/
}
