package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.WfActivityInstanceDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-09
 */
public interface WfActivtityInstanceMapper extends CrudRepository<WfActivityInstanceDO, String> {
    int decrementActiveNum(String id);
    Iterable<WfActivityInstanceDO> findAllByPiIdAndUserTaskNo(String piId, String userTaskNo);
    void deleteByPiId(String piId);
}