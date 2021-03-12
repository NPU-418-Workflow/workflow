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
@Table(name = "wf_activity_history_instance")
public class ActivityHistoryInstanceDO extends BaseDO {
    private static final long serialVersionUID=1L;

    private String actId;


    /**
     * 运行时记录名称
     */
    private String aiName;

    /**
     * 运行时执行人类型
     */
    private String aiAssignerType;

    /**
     * 运行时执行人标识
     */
    private String aiAssignerId;

    /**
     * 挂接业务表单主键
     */
    private String bfId;

    /**
     * 运行时记录状态
     */
    private String aiStatus;

    /**
     * 活动开始时间
     */
    private Date aiCreateTime;

    /**
     * 活动更新时间
     */
    private Date aiUpdateTime;

    /**
     * xml活动编号
     */
    private String userTaskNo;

    /**
     * 活动类型(会签、可回退、普通等等)
     */
    private String aiCategory;

    private String piId;

    /**
     * 流程定义标识(外键)
     */
    private String pdId;

    /**
     * 当前活动未完成实例个数
     */
    private int activeTiNum;
}
