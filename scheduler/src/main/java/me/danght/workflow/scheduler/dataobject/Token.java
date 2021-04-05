package me.danght.workflow.scheduler.dataobject;

import io.quarkus.redis.client.RedisClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.danght.workflow.common.api.schduler.ProcessInstanceService;
import me.danght.workflow.scheduler.dao.TaskInstanceRepository;
import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.element.Node;
import me.danght.workflow.scheduler.service.ActivityInstanceService;
import me.danght.workflow.scheduler.service.ProcessParamsRecordService;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 令牌对象，持久化类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
@Table(name = "token")
public class Token extends BaseDO {

    protected Date endTime;

    /**
     * 数据库用,当前持有令牌的节点的编号
     */
    protected String elementNo;

    /**
     * 当前持有令牌的节点
     */
    @Transient
    protected Node currentNode = null;

    /**
     * 父token，如果存在
     */
    @Transient
    protected Token parent = null;

    private String parentId;

    /**
     * 流程实例主键
     */
    protected String piId;

    /**
     * 所属流程的流程定义主键，用于取表单数据
     */
    protected String pdId;

    protected int childNum;

    /**
     * 孩子
     */
    @Transient
    List<Token> children = new ArrayList<>();

    /**
     * 非UserTask节点不会使用signal信号驱动，是通过execute自动转移，而也只有UserTask节点需要调用getDefaultOutgoings()方法
     */
    public void signal(ProcessParamsRecordService processParamsRecordService,
                       TokenRepository tokenRepository,
                       TaskInstanceRepository taskInstanceRepository,
                       ActivityInstanceService activityInstanceService,
                       ProcessInstanceService processInstanceService,
                       RedisClient redisClient){
        if(currentNode == null || currentNode.getDefaultOutgoing() == null)
            return;
        currentNode.leave(this, processParamsRecordService, tokenRepository, taskInstanceRepository, activityInstanceService, processInstanceService, redisClient);
    }


}
