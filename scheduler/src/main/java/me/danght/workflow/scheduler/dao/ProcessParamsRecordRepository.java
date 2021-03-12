package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ProcessParamsRecordDO;
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
public interface ProcessParamsRecordRepository extends CrudRepository<ProcessParamsRecordDO, String> {
    void deleteByAiId(String aiId);

    Optional<ProcessParamsRecordDO> findByEnginePpNameAndTiId(String enginePpName, String tiId);

    Optional<ProcessParamsRecordDO> findByEnginePpNameAndAiId(String enginePpName, String aiId);

    Iterable<ProcessParamsRecordDO> findAllByTiId(String tiId);

    Iterable<ProcessParamsRecordDO> findAllByTiIdAndStatusAndPpRecordLevel(String tiId, String status, String ppRecordLevel);

    Iterable<ProcessParamsRecordDO> findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(String ppRelationId, String aiId, String status, String ppRecordLevel);

    Iterable<ProcessParamsRecordDO> findAllByEnginePpNameAndAiIdOrderByCreatetimeDesc(String enginePpName, String aiId, String status);
}
