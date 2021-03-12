package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.WfProcessParamsRecordDO;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-23
 */
public interface WfProcessParamsRecordMapper extends CrudRepository<WfProcessParamsRecordDO, String> {
    int updateParamsValue(WfProcessParamsRecordDO wfProcessParamsRecordDO);

}
