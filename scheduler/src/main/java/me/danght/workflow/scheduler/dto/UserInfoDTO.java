package me.danght.workflow.scheduler.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID=1L;

    private String id;

    private String uiName;

    private String tenantId;

    private String giId;

    private String uiNo;
}
