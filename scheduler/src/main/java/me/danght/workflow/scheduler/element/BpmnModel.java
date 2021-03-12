package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 流程描述模型类
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-23
 */
@Data
@Accessors(chain = true)
public class BpmnModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * BpmnModel唯一标识
     */
    protected String no;

    /**
     * BpmnModel名称
     */
    protected String name;

    /**
     * 流程
     */
    protected Process process;
}
