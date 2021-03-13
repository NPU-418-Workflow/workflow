package me.danght.workflow.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.danght.workflow.app.dto.LeaveInfoDTO;
import me.danght.workflow.app.dto.UserInfoDTO;
import me.danght.workflow.app.service.LeaveInfoService;
import me.danght.workflow.app.service.UserInfoService;
import me.danght.workflow.common.api.form.ClientProcessService;
import me.danght.workflow.common.api.form.ClientTaskService;
import me.danght.workflow.common.bo.ProcessDefinitionBO;
import me.danght.workflow.common.bo.ProcessInstanceBO;
import me.danght.workflow.common.bo.TaskInstanceBO;
import me.danght.workflow.common.constant.ParamType;
import me.danght.workflow.common.constant.ProcessInstanceState;
import me.danght.workflow.common.dataobject.ParmObject;
import me.danght.workflow.common.serialization.BaseMapper;
import org.apache.dubbo.config.annotation.DubboReference;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserInfoService userInfoService;

    @Inject
    LeaveInfoService leaveInfoService;

    @DubboReference
    ClientProcessService clientProcessService;

    @DubboReference
    ClientTaskService clientTaskService;


    @POST
    @Path("/completeApproval")
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

    @POST
    @Path("/queryUnObtainList")
    public String queryUnObtainList(@QueryParam("uiId") String uiId){
        UserInfoDTO userInfoDTO = userInfoService.queryUser(uiId);

        List<TaskInstanceBO> wfTaskInstanceBOList = clientTaskService.selectUnObtainTask(userInfoDTO.getGiId());
        JSONArray wfdArray = JSON.parseArray(JSONObject.toJSONString(wfTaskInstanceBOList));
        return wfdArray.toJSONString();
    }

    @POST
    @Path("/queryProcessList")
    public String queryProcessList(@QueryParam("uiId") String uiId){
        List<ProcessInstanceBO> wfProcessInstanceBOList = clientProcessService.getProcessListByUserId(uiId);
        JSONArray wfdArray = JSON.parseArray(JSONObject.toJSONString(wfProcessInstanceBOList));
        return wfdArray.toJSONString();
    }

    @POST
    @Path("/obtainTask")
    public String obtainTask(@QueryParam("taskId") String taskId, @QueryParam("uiId") String uiId) {
        clientTaskService.obtainTask(taskId,uiId);
        return "1";
    }

    @POST
    @Path("/loginValidate")
    public String loginValidate(@QueryParam("uiName") String uiName) {
        UserInfoDTO userInfoDTO = userInfoService.validateUser(uiName);
        return JSON.toJSONString(userInfoDTO);
    }

    @POST
    @Path("/addLeave")
    public String addLeave(
            @QueryParam("uiId") String uiId,
            @QueryParam("durations") String durations,
            @QueryParam("pdId") String pdId) {
        LeaveInfoDTO leaveInfoDTO = new LeaveInfoDTO().setDurations(Integer.parseInt(durations))
                .setUiId(uiId);
        leaveInfoDTO = leaveInfoService.addLeave(leaveInfoDTO);
        UserInfoDTO userInfoDTO = userInfoService.queryUser(uiId);
        Map<String, ParmObject> requiredData = new HashMap<>();
        ParmObject parmObject = new ParmObject()
                .setPpType(ParamType.PARAM_TYPE_INT)
                .setVal(Integer.parseInt(durations));
        requiredData.put("durations",parmObject);
        clientProcessService.startProcess(pdId,userInfoDTO.getUiName() + "请假申请",userInfoDTO.getId(),leaveInfoDTO.getId(),requiredData);
        return "1";
    }

    @POST
    @Path("/personinfo")
    public String personinfo(
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
    @Path("/completedRead")
    public String completedRead(
            @QueryParam("pdId") String pdId,
            @QueryParam("tiId") String tiId,
            @QueryParam("userTaskNo") String userTaskNo) {
        clientTaskService.completeTask(tiId,pdId,userTaskNo, new HashMap<>());
        return "1";
    }

    @POST
    @Path("/queryApprovalList")
    public String queryApprovalList(
            @QueryParam("uiId") String uiId,
            @QueryParam("uiName") String uiName,
            @QueryParam("tenantId") String tenantId){
        List<ProcessDefinitionBO> wfProcessDefinitionBOList = clientProcessService.queryDefinitionList();
        JSONArray wfdArray = JSON.parseArray(JSONObject.toJSONString(wfProcessDefinitionBOList));
        return wfdArray.toJSONString();
    }

    @POST
    @Path("/queryUnTaskList")
    public String queryUnTaskList(@QueryParam("uiId") String uiId){
        List<TaskInstanceBO> wfTaskInstanceBOList = clientTaskService.selectUnCompletedTask(uiId,"0");
        JSONArray wfdArray = JSON.parseArray(JSONObject.toJSONString(wfTaskInstanceBOList));
        return wfdArray.toJSONString();
    }

    @POST
    @Path("/queryLeaveInfo")
    public String queryLeaveInfo(@QueryParam("piBusinesskey") String piBusinesskey) {
        LeaveInfoDTO leaveInfoDTO = leaveInfoService.selectById(piBusinesskey);
        return JSON.toJSONString(leaveInfoDTO);
    }
}
