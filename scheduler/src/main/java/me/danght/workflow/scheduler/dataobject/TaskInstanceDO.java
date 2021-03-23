package me.danght.workflow.scheduler.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "task_instance")
public class TaskInstanceDO extends BaseDO {
    private static final long serialVersionUID=1L;
    /**
     * 任务名称
     */
    private String tiName;

    /**
     * 任务参与者/执行人
     */
    private String tiAssigner;

    /**
     * 任务状态01正在运行02已完成03该次活动已结束04已移到历史库
     */
    private String tiStatus;

    /**
     * 任务关联表单外键
     */
    private String bfId;

    /**
     * 所属活动实例标识
     */
    private String aiId;

    /**
     * 任务结束时间
     */
    private Date endTime;

    private String piId;

    /**
     * 流程定义标识(外键)
     */
    private String pdId;

    private String tiAssignerType;

    private String userTaskNo;

}
