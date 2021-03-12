package me.danght.workflow.scheduler.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * 流程模板文件实体
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "wf_process_template")
public class WfProcessTemplateDO extends BaseDO {
    private static final long serialVersionUID=1L;

    /**
     * 流程描述文件内容
     */
    private String ptContent;

    /**
     * 流程描述文件名称
     */
    private String ptFilename;

}

