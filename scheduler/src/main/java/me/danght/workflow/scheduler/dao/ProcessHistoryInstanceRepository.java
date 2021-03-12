package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ProcessHistoryInstanceDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-08
 */
public interface ProcessHistoryInstanceRepository extends CrudRepository<ProcessHistoryInstanceDO, String> {

}
