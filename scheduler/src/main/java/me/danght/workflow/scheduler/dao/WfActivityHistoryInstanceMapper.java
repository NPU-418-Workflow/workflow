package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.WfActivityHistoryInstanceDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-11
 */
public interface WfActivityHistoryInstanceMapper extends CrudRepository<WfActivityHistoryInstanceDO, String> {

}