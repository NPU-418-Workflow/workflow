package me.danght.workflow.scheduler.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用于参数关系表的查询，目前仅用于查询一条relation，这三个属性可以确定一条记录
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-09
 */
@Data
@Accessors(chain = true)
public class ProcessParamsRelationQueryDTO implements Serializable {
    private static final long serialVersionUID=1L;
    /**
     * 对应的流程id
     */
    private String pdId;

    /**
     * 与所属流程的活动(环节)相关联，但并不是关联的表中的id，而是流程描述xml中的id
     */
    private String taskNo;

    private String businessName;
}
