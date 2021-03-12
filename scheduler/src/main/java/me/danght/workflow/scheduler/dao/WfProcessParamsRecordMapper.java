package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.WfProcessParamsRecordDO;
import org.apache.zookeeper.Op;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-23
 */
public interface WfProcessParamsRecordMapper extends CrudRepository<WfProcessParamsRecordDO, String> {
    void deleteByAiId(String aiId);

    Optional<WfProcessParamsRecordDO> findByEnginePpNameAndTiId(String enginePpName, String tiId);

    Optional<WfProcessParamsRecordDO> findByEnginePpNameAndAiId(String enginePpName, String aiId);

    Iterable<WfProcessParamsRecordDO> findAllByTiId(String tiId);

    Iterable<WfProcessParamsRecordDO> findAllByTiIdAndStatusAndPpRecordLevel(String tiId, String status, String ppRecordLevel);

    Iterable<WfProcessParamsRecordDO> findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(String ppRelationId, String aiId, String status, String ppRecordLevel);

    Iterable<WfProcessParamsRecordDO> findAllByEnginePpNameAndAiIdOrderByCreatetimeDesc(String enginePpName, String aiId, String status);
}
