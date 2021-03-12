package me.danght.workflow.common.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author DangHT
 * @date 2021/03/05
 */
@Data
@Accessors(chain = true)
public class ProcessDefinitionBO implements Serializable {
    private static final long serialVersionUID=1L;
    /**
     * 主键
     */
    private String id;
    /**
     * 流程定义名称(中文，从xml中抽取)
     */
    private String pdName;

    /**
     * 对应的流程资源文件no，即唯一标识，由画图软件自动生成，从xml中抽取
     */
    private String pdNo;

    /**
     * 流程模板主键
     */
    private String ptId;

    /**
     * 流程描述文件内容，附加到这里，操作方便
     */
    private String ptContent;

    private String startForm;
}
