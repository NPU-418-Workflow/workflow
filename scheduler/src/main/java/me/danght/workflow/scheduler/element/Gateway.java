package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网关抽象基类
 * </p>
 *
 * @author wenxiang
 * @author DangHT
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class Gateway extends Node {

    /**
     * 网关名称
     */
    protected String name;

}
