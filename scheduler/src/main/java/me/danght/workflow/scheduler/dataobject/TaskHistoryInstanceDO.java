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
@Table(name = "wf_task_history_instance")
public class TaskHistoryInstanceDO extends BaseDO {
    private static final long serialVersionUID=1L;

    /**
     * 运行时的任务实例主键
     */
    private String tiId;


    /**
     * 对应活动运行时主键
     */
    private String aiId;

    /**
     * 任务名称
     */
    private String tiName;

    /**
     * 任务状态0正在运行1已完成
     */
    private String tiStatus;

    /**
     * 任务关联表单外键
     */
    private String bfId;

    /**
     * 运行时任务开始时间
     */
    private Date tiCreateTime;

    /**
     * 运行时任务状态更新时间
     */
    private Date tiUpdateTime;

    /**
     * 运行时任务结束时间
     */
    private Date tiEndTime;
}
