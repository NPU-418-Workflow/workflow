package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ProcessParamsRecordDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
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

    List<ProcessParamsRecordDO> findAllByTiId(String tiId);

    List<ProcessParamsRecordDO> findAllByTiIdAndStatusAndPpRecordLevel(String tiId, String status, String ppRecordLevel);

    List<ProcessParamsRecordDO> findAllByPpRelationIdAndAiIdAndStatusAndPpRecordLevel(String ppRelationId, String aiId, String status, String ppRecordLevel);

    List<ProcessParamsRecordDO> findAllByEnginePpNameAndAiIdAndStatusOrderByCreateTimeDesc(String enginePpName, String aiId, String status);
}
