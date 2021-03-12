package me.danght.workflow.scheduler.constant;

/**
 * <p>
 *  该状态参数针对活动被驳回后重新开启，当检测到要开启的活动之前已经开启过时，
 *  就更改之前的任务状态置为失效。
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-23
 */
public class ProcessParamState {
    public static final String PROCESS_PARAM_EFFECT = "01";	    //01生效
    public static final String PROCESS_PARAM_FAILURE = "02";    //02失效
}
