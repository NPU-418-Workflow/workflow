package me.danght.workflow.scheduler.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ProcessParamsRecordBO implements Serializable {
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
     * 流程参数对应的一条记录值
     */
    private String ppRecordValue;

    /**
     * 所属流程参数关系的id
     */
    private String ppRelationId;

    /**
     * 参数所属任务实例标志(数据库主键)(任务级参数)
     */
    private String tiId;

    /**
     * 参数所属活动实例标识(数据库主键)(活动级参数)
     */
    private String aiId;

    /**
     * 参数所属流程实例标识(数据库主键)(流程级参数)
     */
    private String piId;

    /**
     * 该参数记录状态0有效1无效，主要是针对驳回后产生新的参数记录
     */
    private String status;

    /**
     * 数据类型(附加)
     */
    private String ppType;

    /**
     * 参数记录级别
     */
    private String ppRecordLevel;

    /**
     * 流程引擎中读取该参数时使用的名称(新增)
     */
    private String enginePpName;

}
