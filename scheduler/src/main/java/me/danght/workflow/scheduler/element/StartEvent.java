package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * <p>
 * 无指定开始事件类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-12-17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class StartEvent extends Event {

    /**
     * 挂接表单页面
     */
    protected String pageKey;

    protected List<DataParam> paramList;

}
