package me.danght.workflow.scheduler.dataobject;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体对象
 */
@MappedSuperclass
public class BaseDO implements Serializable {

    /**
     * 主键
     */
    @Id
    protected String id;

    /**
     * 创建时间
     */
    protected Date createtime;

    /**
     * 最后更新时间
     */
    protected Date updatetime;

    @Override
    public String toString() {
        return "BaseDO{" +
                "createTime=" + createtime +
                ", updateTime=" + updatetime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
