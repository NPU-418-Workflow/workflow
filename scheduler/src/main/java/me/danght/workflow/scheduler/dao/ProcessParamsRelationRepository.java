package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ProcessParamsRelationDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-09
 */
public interface ProcessParamsRelationRepository extends CrudRepository<ProcessParamsRelationDO, String> {
    Optional<ProcessParamsRelationDO> findByBusinessNameAndPdIdAndTaskNo(String businessName, String pdId, String taskNo);
}