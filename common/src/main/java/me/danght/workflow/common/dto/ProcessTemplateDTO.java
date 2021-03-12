package me.danght.workflow.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 流程模板文件DTO
 * </p>
 *
 * @author wenxiang
 * @since 2019-10-11
 */
@Data
@Accessors(chain = true)
public class ProcessTemplateDTO implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 流程描述文件内容
     */
    private String ptContent;

    /**
     * 流程描述文件名称
     */
    private String ptFilename;

}

