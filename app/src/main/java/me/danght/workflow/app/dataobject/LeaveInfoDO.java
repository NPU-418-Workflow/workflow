package me.danght.workflow.app.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "leave")
public class LeaveInfoDO implements Serializable {
    private static final long serialVersionUID=1L;
    /**
     * 主键
     */
    @Id
    private String id;

    private String uiId;

    private Integer durations;

}
