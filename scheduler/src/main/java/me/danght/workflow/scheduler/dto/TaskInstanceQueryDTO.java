package me.danght.workflow.scheduler.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用于Task实例表的查询，目前仅用于关联任务完成情况判断计数
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-09
 */
@Data
@Accessors(chain = true)
public class TaskInstanceQueryDTO implements Serializable {
    private static final long serialVersionUID=1L;

    private String tiStatus;

    private String aiId;
}
