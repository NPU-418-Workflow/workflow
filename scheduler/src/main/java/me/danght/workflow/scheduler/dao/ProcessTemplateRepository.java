package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ProcessTemplateDO;
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
public interface ProcessTemplateRepository extends CrudRepository<ProcessTemplateDO, String> {
    Optional<ProcessTemplateDO> findByPtFilename(String ptFileName);
}
