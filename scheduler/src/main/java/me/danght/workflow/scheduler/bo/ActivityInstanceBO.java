package me.danght.workflow.scheduler.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DangHT
 * @date 2021/03/05
 */
@Data
@Accessors(chain = true)
public class ActivityInstanceBO implements Serializable {
    private static final long serialVersionUID=1L;

    private String id;
    /**
     * 活动名称（当前环节业务描述）
     */
    private String aiName;

    /**
     * 活动状态(0运行中1已完成)
     */
    private String aiStatus;

    /**
     * 活动参与者，可以是外键
     */
    private String aiAssignerId;

    /**
     * 活动参与者种类（0个人1职位）
     */
    private String aiAssignerType;

    /**
     * 活动关联表单外键
     */
    private String bfId;

    /**
     * xml活动编号
     */
    private String userTaskNo;

    /**
     * 活动类型(会签、可回退、普通等等)
     */
    private String aiCategory;

    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;

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
