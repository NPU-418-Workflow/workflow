package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.WfTaskInstanceDO;
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
public interface WfTaskInstanceMapper extends CrudRepository<WfTaskInstanceDO, String> {

    void updateAssignerType(String id,String ti_assigner);

    int countByTiStatusAndAiId(String tiStatus, String aiId);

    Optional<WfTaskInstanceDO> findByPiId(String piId);

    Iterable<WfTaskInstanceDO> findAllByAiId(String aiId);

    Iterable<WfTaskInstanceDO> findAllByTiStatusAndAiId(String tiStatus, String aiId);

    Iterable<WfTaskInstanceDO> findAllByTiAssignerAndTiAssignerTrue(String tiAssigner, String tiAssignerType);
}
