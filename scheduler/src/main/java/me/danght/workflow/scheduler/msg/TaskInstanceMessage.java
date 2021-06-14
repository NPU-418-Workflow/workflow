package me.danght.workflow.scheduler.msg;

import lombok.Data;
import lombok.experimental.Accessors;
import me.danght.workflow.scheduler.dataobject.ParmObject;

import java.util.Map;

/**
 * <p>
 * 任务实例mq用调度request message对象
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-09
 */
@Data
@Accessors(chain = true)
public class TaskInstanceMessage {

    /**
     * 主键
     */
    private String id;
    /**
     * 任务名称
     */
    //private String tiName;

    /**
     * 所属活动实例标识
     */
    //private String aiId;

    /**
     * 该任务对应的必填项数据
     */
    Map<String, ParmObject> requiredData;

    /**
     * 流程实例主键
     */
    //private String piId;


    //private String pdId;
}
