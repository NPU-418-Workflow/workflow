package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ActivityInstanceDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-09
 */
public interface ActivityInstanceRepository extends CrudRepository<ActivityInstanceDO, String> {

    Iterable<ActivityInstanceDO> findAllByPiIdAndUserTaskNo(String piId, String userTaskNo);

    void deleteByPiId(String piId);
}