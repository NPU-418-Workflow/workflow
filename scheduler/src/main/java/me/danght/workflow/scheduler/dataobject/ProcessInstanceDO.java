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
@Table(name = "process_instance")
public class ProcessInstanceDO extends BaseDO {
    private static final long serialVersionUID=1L;

    /**
     * 流程实例名称(自动拼接用户名+模板名称)
     */
    private String piName;

    /**
     * 流程定义标识(外键)
     */
    private String pdId;

    /**
     * 流程执行状态0未开启01运行中02已完成
     */
    private String piStatus;

    /**
     * 流程发起人(具体用户ID)
     */
    private String piStarter;

    /**
     * 流程结束时间
     */
    private Date endTime;

    /**
     * 流程关联的业务数据主键
     */
    private String piBusinessKey;
}
