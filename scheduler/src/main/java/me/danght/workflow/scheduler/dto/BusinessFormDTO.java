package me.danght.workflow.scheduler.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BusinessFormDTO implements Serializable {
    private static final long serialVersionUID=1L;

    private String id;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;

    private String formName;

    private String formUrl;

}
