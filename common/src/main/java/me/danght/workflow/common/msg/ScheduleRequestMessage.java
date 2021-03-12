package me.danght.workflow.common.msg;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 调度器调度请求消息
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-08
 */
@Data
@Accessors(chain = true)
public class ScheduleRequestMessage {
    public static final String TOPIC = "ScheduleComponent";

    /**
     * 流程管理器将流程实例中的调度所需的属性发给调度器，目前的设计是流程管理器
     */
    private ProcessInstanceMessage processInstanceMessage;

    /**
     * 任务管理器将任务实例中的调度所需的属性以及必填项发给调度器
     */
    private TaskInstanceMessage taskInstanceMessage;

}
