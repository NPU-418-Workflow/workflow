package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.TaskInstanceDO;
import org.springframework.data.repository.CrudRepository;

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

    void updateAssignerType(String id,String ti_assigner);

    int countByTiStatusAndAiId(String tiStatus, String aiId);

    Optional<TaskInstanceDO> findByPiId(String piId);

    Iterable<TaskInstanceDO> findAllByAiId(String aiId);

    Iterable<TaskInstanceDO> findAllByTiStatusAndAiId(String tiStatus, String aiId);

    Iterable<TaskInstanceDO> findAllByTiAssignerAndTiAssignerTrue(String tiAssigner, String tiAssignerType);
}
