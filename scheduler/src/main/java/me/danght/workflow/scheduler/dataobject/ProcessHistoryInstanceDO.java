package me.danght.workflow.scheduler.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "process_history_instance")
public class ProcessHistoryInstanceDO extends BaseDO {
    private static final long serialVersionUID=1L;

    private String piId;

    private String piName;

    private String pdId;

    /**
     * 当前记录动作
     */
    private String piStatus;

    private String piStarter;

    private String piBusinessKey;

    private Date piCreateTime;

    private Date piUpdateTime;

    private Date piEndTime;
}
