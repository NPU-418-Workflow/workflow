package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.api.schduler.ProcessInstanceService;
import me.danght.workflow.scheduler.dao.TokenMapper;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.service.WfActivtityInstanceService;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * <p>
 * 无指定结束事件类——流程描述类组成要素
 * </p>
 *
 * @author wenxiang
 * @since 2019-09-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class EndEvent extends Event implements Serializable {
    private static final long serialVersionUID = 1L;
    //private ApplicationContext applicationContext =  SpringUtil.getApplicationContext();

    //TokenMapper tokenMapper = applicationContext.getBean(TokenMapper.class);

    //WfActivtityInstanceService wfActivtityInstanceService = applicationContext.getBean(WfActivtityInstanceService.class);;

    //private RocketMQTemplate rocketMQTemplate = applicationContext.getBean(RocketMQTemplate.class);

    @Inject
    TokenMapper tokenMapper;

    @Inject
    WfActivtityInstanceService wfActivtityInstanceService;

    @Inject
    ProcessInstanceService processInstanceService;

    @Override
    public void execute(Token token){
        //结束流程
        tokenMapper.deleteById(token.getId());
        wfActivtityInstanceService.clearActivityOfProcess(token.getPiId());
        processInstanceService.endProcess(token.getPiId());
    }

/*    private void sendProcessRequestMessage(String piId) {
        SpringUtil.getBean(RocketMQTemplate.class).convertAndSend(ProcessRequestMessage.TOPIC, new ProcessRequestMessage().setPiId(piId));
    }*/
}
