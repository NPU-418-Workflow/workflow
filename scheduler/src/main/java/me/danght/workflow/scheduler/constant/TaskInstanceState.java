package me.danght.workflow.scheduler.constant;

public class TaskInstanceState {

    /**
     * 01: 运行中
     * 02: 已完成
     * 03: 该次活动已结束
     * 04: 已转移至历史库
     */
    public static final String TASK_INSTANCE_STATE_RUNNING = "01";
    public static final String TASK_INSTANCE_STATE_COMPLETED = "02";
    public static final String TASK_INSTANCE_STATE_DISABLED = "03";
    public static final String TASK_INSTANCE_STATE_PAST = "04";

}
