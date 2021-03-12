package me.danght.workflow.scheduler.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * 流程参数实体
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "wf_process_params_record")
public class ProcessParamsRecordDO extends BaseDO {
    private static final long serialVersionUID=1L;

    /**
     * 流程参数对应的一条记录值
     */
    private String ppRecordValue;

    /**
     * 所属流程参数关系的id
     */
    private String ppRelationId;

    /**
     * 参数所属任务实例标志(数据库主键)(任务级参数)
     */
    private String tiId;

    /**
     * 参数所属活动实例标识(数据库主键)(活动级参数)
     */
    private String aiId;

    /**
     * 参数所属流程实例标识(数据库主键)(流程级参数)
     */
    private String piId;

    /**
     * 该参数记录状态0有效1无效，主要是针对驳回后产生新的参数记录
     */
    private String status;

    /**
     * 参数记录级别
     */
    private String ppRecordLevel;

    /**
     * 流程引擎中读取该参数时使用的名称(新增)
     */
    private String enginePpName;
    private String ppType;
}
