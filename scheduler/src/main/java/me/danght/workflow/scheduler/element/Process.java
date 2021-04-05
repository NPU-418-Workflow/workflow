package me.danght.workflow.scheduler.element;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 流程类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Process extends BaseElement implements Serializable {
    private static final long serialVersionUID = 1L;

    public Process() {
        userTaskList = new ArrayList<>();
        sequenceFlowList = new ArrayList<>();
        gatewayList = new ArrayList<>();
        eventList = new ArrayList<>();
    }

    /**
     * 流程名称
     */
    protected String name;

    /**
     * 流程所含活动集合
     */
    protected List<UserTask> userTaskList;

    /**
     * 流程所含顺序流集合
     */
    protected List<SequenceFlow> sequenceFlowList;

    /**
     * 流程事件集合
     */
    protected List<Event> eventList;

    /**
     * 流程网关集合
     */
    protected List<Gateway> gatewayList;

    protected StartEvent startEvent;
}
