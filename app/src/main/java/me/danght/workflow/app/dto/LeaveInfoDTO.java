package me.danght.workflow.app.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LeaveInfoDTO implements Serializable {
    private static final long serialVersionUID=1L;
    /**
     * 主键
     */
    private String id;

    private String uiName;

    private String uiId;

    private Integer durations;

}
