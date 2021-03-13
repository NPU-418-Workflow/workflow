package me.danght.workflow.form.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "wf_business_form")
public class BusinessFormDO extends BaseDO {
    private static final long serialVersionUID=1L;

    private String formName;

    private String formUrl;

}
