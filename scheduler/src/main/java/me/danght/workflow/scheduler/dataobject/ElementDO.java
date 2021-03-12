package me.danght.workflow.scheduler.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "wf_element")
public class ElementDO extends BaseDO {
    private static final long serialVersionUID=1L;

    /**
     * 元素编号
     */
    private String elementNo;

    /**
     * 元素所属流程主键
     */
    private String elementProcessId;

    /**
     * 元素实际发放令牌数量
     */
    private Integer tokenNumber;

    /**
     * 目前的作用是区分网关作用为分支(1)/聚合(2)
     */
    private Integer elementRole;
}
