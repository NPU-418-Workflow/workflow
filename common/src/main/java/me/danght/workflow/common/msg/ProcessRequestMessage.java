package me.danght.workflow.common.msg;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 调度器调度请求消息
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-08
 */
@Data
@Accessors(chain = true)
public class ProcessRequestMessage {

    /**
     * 流程实例主键
     */
    private String  piId;
}
