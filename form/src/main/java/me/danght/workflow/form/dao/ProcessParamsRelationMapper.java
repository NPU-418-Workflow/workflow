package me.danght.workflow.form.dao;

import me.danght.workflow.form.dataobject.ProcessParamsRelationDO;
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
public interface ProcessParamsRelationMapper extends CrudRepository<ProcessParamsRelationDO, String> {
    Optional<ProcessParamsRelationDO> findByBusinessNameAndPdIdAndTaskNo(String businessName, String pdId, String taskNo);
}