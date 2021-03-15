package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-08
 */
public interface TaskInstanceRepository extends CrudRepository<TaskInstanceDO, String> {

    long countByTiStatusAndAiId(String tiStatus, String aiId);

    Optional<TaskInstanceDO> findByPiId(String piId);

    List<TaskInstanceDO> findAllByAiId(String aiId);

    List<TaskInstanceDO> findAllByTiStatusAndAiId(String tiStatus, String aiId);

    List<TaskInstanceDO> findAllByTiAssignerAndTiAssignerType(String tiAssigner, String tiAssignerType);
}
