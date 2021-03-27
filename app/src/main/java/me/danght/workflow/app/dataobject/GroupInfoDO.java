package me.danght.workflow.app.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "group")
public class GroupInfoDO implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String giName;

    private String giNo;

    private String tenantId;

}
