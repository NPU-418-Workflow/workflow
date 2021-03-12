package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ProcessInstanceDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-07
 */
public interface ProcessInstanceRepository extends CrudRepository<ProcessInstanceDO, String> {
    Iterable<ProcessInstanceDO> findAllByPiStarter(String piStarter);
}
