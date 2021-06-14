package me.danght.workflow.scheduler.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TaskHistoryInstanceDTO implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
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

    private String piId;

    /**
     * 流程定义标识(外键)
     */
    private String pdId;
}
