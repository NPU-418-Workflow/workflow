package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.TaskHistoryInstanceDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-10
 */
public interface TaskHistoryInstanceRepository extends CrudRepository<TaskHistoryInstanceDO, String> {

}