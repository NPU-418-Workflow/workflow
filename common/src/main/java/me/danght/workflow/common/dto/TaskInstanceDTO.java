package me.danght.workflow.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TaskInstanceDTO implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 最后更新时间
     */
    private Date updatetime;

    /**
     * 任务名称
     */
    private String tiName;

    /**
     * 任务参与者/执行人
     */
    private String tiAssigner;

    /**
     * 任务状态01正在运行02已完成03已移到历史库
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
    private Date endtime;

    private String piId;

    /**
     * 流程定义标识(外键)
     */
    private String pdId;

    private String tiAssignerType;

    private String usertaskNo;
}
