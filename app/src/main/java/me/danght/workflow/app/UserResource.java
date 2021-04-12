package me.danght.workflow.app;

import me.danght.workflow.app.dataobject.UserInfoDO;
import me.danght.workflow.app.dto.UserInfoDTO;
import me.danght.workflow.app.service.UserInfoService;
import me.danght.workflow.common.api.form.ClientProcessService;
import me.danght.workflow.common.api.form.ClientTaskService;
import me.danght.workflow.common.bo.ProcessDefinitionBO;
import me.danght.workflow.common.bo.ProcessInstanceBO;
import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.constant.ProcessInstanceState;
import org.apache.dubbo.config.annotation.DubboReference;

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

    @Inject
    UserInfoService userInfoService;

    @DubboReference(check = false)
    ClientProcessService clientProcessService;

    @DubboReference(check = false)
    ClientTaskService clientTaskService;

    @GET
    public Iterable<UserInfoDO> getAllUsers() {
        return userInfoService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public UserInfoDTO getUser(@PathParam("id") String id) {
        return userInfoService.getUserById(id);
    }

    @GET
    @Path("queryApprovalList")
    public List<ProcessDefinitionBO> queryApprovalList(){
        return clientProcessService.queryDefinitionList();
    }

    @GET
    @Path("queryUnTaskList")
    public List<TaskInstanceBO> queryUnTaskList(@QueryParam("uiId") String uiId){
        return clientTaskService.selectUnCompletedTask(uiId,"0");
    }

    @GET
    @Path("queryUnObtainList")
    public List<TaskInstanceBO> queryUnObtainList(@QueryParam("uiId") String uiId){
        UserInfoDTO userInfoDTO = userInfoService.getUserById(uiId);
        return clientTaskService.selectUnObtainTask(userInfoDTO.getGiId());
    }

    @GET
    @Path("queryProcessList")
    public List<ProcessInstanceBO> queryProcessList(@QueryParam("uiId") String uiId){
        return clientProcessService.getProcessListByUserId(uiId);
    }

    @POST
    @Path("obtainTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public String obtainTask(@QueryParam("taskId") String taskId, @QueryParam("uiId") String uiId) {
        clientTaskService.obtainTask(taskId,uiId);
        return "1";
    }

    @POST
    @Path("completedRead")
    @Consumes(MediaType.APPLICATION_JSON)
    public String completedRead(
            @QueryParam("pdId") String pdId,
            @QueryParam("tiId") String tiId,
            @QueryParam("userTaskNo") String userTaskNo) {
        clientTaskService.completeTask(tiId,pdId,userTaskNo, new HashMap<>());
        return "1";
    }

    @POST
    @Path("loginValidate")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserInfoDTO loginValidate(@QueryParam("uiName") String uiName) {
        return userInfoService.validateUser(uiName);
    }

    @POST
    @Path("assignTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public String assignTask(
            @QueryParam("pdId") String pdId,
            @QueryParam("tiId") String tiId,
            @QueryParam("userTaskNo") String userTaskNo,
            @QueryParam("businessDynamicAssignee01") String businessDynamicAssignee01) {
        Map<String, Object> requiredData = new HashMap<>();
        requiredData.put("businessDynamicAssignee01",businessDynamicAssignee01);
        clientTaskService.completeTask(tiId,pdId,userTaskNo,requiredData);
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
        Map<String, Object> requiredData = new HashMap<String, Object>();
        requiredData.put("businessJudge",businessJudge);
        if(uiId.equals("5") && businessJudge)
            clientProcessService.changeProcessState(piId, ProcessInstanceState.PROCESS_INSTANCE_STATE_PASS);
        if(uiId.equals("5") && !businessJudge)
            clientProcessService.changeProcessState(piId, ProcessInstanceState.PROCESS_INSTANCE_STATE_FAIL);
        clientTaskService.completeTask(tiId,pdId,userTaskNo,requiredData);
        return "1";
    }

}
