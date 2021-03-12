package me.danght.workflow.scheduler.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "wf_activtity_instance")
public class WfActivityInstanceDO extends BaseDO {
    private static final long serialVersionUID=1L;

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
     * 即席活动执行人
     *//*
    private String aiDynamicAssignerId;

    *//**
     * 即席活动执行人种类（0个人1职位）
     *//*
    private String aiDynamicAssignerType;*/

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
