package me.danght.workflow.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ProcessInstanceStartDTO implements Serializable {
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
     * 流程发起人(具体用户ID)
     */
    private String piStarter;

    /**
     * 流程关联的业务数据主键
     */
    private String piBusinesskey;
}

