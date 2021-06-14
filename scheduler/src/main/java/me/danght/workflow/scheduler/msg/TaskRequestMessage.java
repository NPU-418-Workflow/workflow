package me.danght.workflow.scheduler.msg;

import lombok.Data;
import lombok.experimental.Accessors;
import me.danght.workflow.scheduler.bo.ActivityInstanceBO;

import java.util.List;

/**
 * <p>
 * 请求任务管理器开启任务
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-11
 */
@Data
@Accessors(chain = true)
public class TaskRequestMessage {

    List<ActivityInstanceBO> activityInstanceBOList;
}
