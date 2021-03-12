package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.WfProcessHistoryInstanceDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-08
 */
public interface WfProcessHistoryInstanceMapper extends CrudRepository<WfProcessHistoryInstanceDO, String> {

}
