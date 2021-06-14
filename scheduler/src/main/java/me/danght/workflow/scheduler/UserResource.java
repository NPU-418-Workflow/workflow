package me.danght.workflow.scheduler;

import me.danght.workflow.scheduler.bo.ProcessDefinitionBO;
import me.danght.workflow.scheduler.bo.ProcessInstanceBO;
import me.danght.workflow.scheduler.bo.TaskInstanceBO;
import me.danght.workflow.scheduler.constant.ProcessInstanceState;
import me.danght.workflow.scheduler.dataobject.UserInfoDO;
import me.danght.workflow.scheduler.dto.UserInfoDTO;
import me.danght.workflow.scheduler.service.ClientProcessService;
import me.danght.workflow.scheduler.service.ClientTaskService;
import me.danght.workflow.scheduler.service.UserInfoService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户API
 * @author DangHT
 * @date 2021/04/12
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = Logger.getLogger(UserResource.class);

    @Inject
    UserInfoService userInfoService;

    @Inject
    ClientProcessService clientProcessService;

    @Inject
    ClientTaskService clientTaskService;

    @GET
    public Iterable<UserInfoDO> getAllUsers() {
        LOGGER.info("查询所有学生信息");
        return userInfoService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public UserInfoDTO getUser(@PathParam("id") String id) {
        LOGGER.info("查询id=" + id + "的学生信息");
        return userInfoService.getUserById(id);
    }

    @GET
    @Path("queryApprovalList")
    public List<ProcessDefinitionBO> queryApprovalList() {
        LOGGER.info("查询全部申请表");
        return clientProcessService.queryDefinitionList();
    }

    @GET
    @Path("queryUnTaskList")
    public List<TaskInstanceBO> queryUnTaskList(@QueryParam("uiId") String uiId){
        LOGGER.info("查询全部未完成任务");
        return clientTaskService.selectUnCompletedTask(uiId,"0");
    }

    @GET
    @Path("queryUnObtainList")
    public List<TaskInstanceBO> queryUnObtainList(@QueryParam("uiId") String uiId){
        LOGGER.info("查询全部未获取任务");
        UserInfoDTO userInfoDTO = userInfoService.getUserById(uiId);
        return clientTaskService.selectUnObtainTask(userInfoDTO.getGiId());
    }

    @GET
    @Path("queryProcessList")
    public List<ProcessInstanceBO> queryProcessList(@QueryParam("uiId") String uiId){
        LOGGER.info("查询全部运转中流程");
        return clientProcessService.getProcessListByUserId(uiId);
    }

    @POST
    @Path("obtainTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public String obtainTask(@QueryParam("taskId") String taskId, @QueryParam("uiId") String uiId) {
        LOGGER.info("用户(id=" + uiId + ")尝试获取任务(id=" + taskId + ")");
        if (clientTaskService.obtainTask(taskId,uiId) != null) {
            LOGGER.info("用户(id=" + uiId + ")获取任务(id=" + taskId + ")成功");
        } else {
            LOGGER.error("用户(id=" + uiId + ")获取任务(id=" + taskId + ")失败");
        }
        return "1";
    }

    @POST
    @Path("completedRead")
    @Consumes(MediaType.APPLICATION_JSON)
    public String completedRead(
            @QueryParam("pdId") String pdId,
            @QueryParam("tiId") String tiId,
            @QueryParam("userTaskNo") String userTaskNo) {
        LOGGER.info("开始完成审阅任务(id=" + tiId + ")");
        if (clientTaskService.completeTask(tiId,pdId,userTaskNo, new HashMap<>())) {
            LOGGER.info("完成审阅任务(id=" + tiId + ")成功");
        } else {
            LOGGER.error("完成审阅任务(id=" + tiId + ")失败");
        }
        return "1";
    }

    @POST
    @Path("loginValidate")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserInfoDTO loginValidate(@QueryParam("uiName") String uiName) {
        LOGGER.info("正在验证用户名: " + uiName);
        UserInfoDTO user = userInfoService.validateUser(uiName);
        if (user != null) {
            LOGGER.info("验证用户名: " + uiName + " 成功");
        } else {
            LOGGER.error("验证用户名: " + uiName + " 失败");
        }
        return user;
    }

    @POST
    @Path("assignTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public String assignTask(
            @QueryParam("pdId") String pdId,
            @QueryParam("tiId") String tiId,
            @QueryParam("userTaskNo") String userTaskNo,
            @QueryParam("businessDynamicAssignee01") String businessDynamicAssignee01) {
        LOGGER.info("正在分配任务(id=" + tiId + "给用户(id=" + businessDynamicAssignee01 + ")");
        Map<String, Object> requiredData = new HashMap<>();
        requiredData.put("businessDynamicAssignee01",businessDynamicAssignee01);
        if (clientTaskService.completeTask(tiId,pdId,userTaskNo,requiredData)) {
            LOGGER.info("分配任务(id=" + tiId + "给用户(id=" + businessDynamicAssignee01 + ")成功");
        } else {
            LOGGER.error("分配任务(id=" + tiId + "给用户(id=" + businessDynamicAssignee01 + ")失败");
        }
        return "1";
    }

    @POST
    @Path("completeApproval")
    @Consumes(MediaType.APPLICATION_JSON)
    public String completeApproval(
            @QueryParam("pdId") String pdId,
            @QueryParam("tiId") String tiId,
            @QueryParam("userTaskNo") String userTaskNo,
            @QueryParam("businessJudge") Boolean businessJudge,
            @QueryParam("uiId") String uiId,
            @QueryParam("piId") String piId) {
        LOGGER.info("正在完成审批任务(id=" + tiId + ")");
        Map<String, Object> requiredData = new HashMap<String, Object>();
        requiredData.put("businessJudge",businessJudge);
        //TODO 这里 uiId=5 的是李总，建议后期维护时将其改为按照角色组别判断一票否决权
        if(uiId.equals("5")) {
            if (businessJudge) {
                LOGGER.info("用户(id=" + uiId + ")直接通过了本次流程");
                clientProcessService.changeProcessState(piId, ProcessInstanceState.PROCESS_INSTANCE_STATE_PASS);
            } else {
                LOGGER.info("用户(id=" + uiId + ")直接否决了本次流程");
                clientProcessService.changeProcessState(piId, ProcessInstanceState.PROCESS_INSTANCE_STATE_FAIL);
            }
        }
        if (clientTaskService.completeTask(tiId,pdId,userTaskNo,requiredData)) {
            LOGGER.info("完成审批任务(id=" + tiId + ")成功");
        } else {
            LOGGER.error("完成审批任务(id=" + tiId + ")失败");
        }
        return "1";
    }

}
