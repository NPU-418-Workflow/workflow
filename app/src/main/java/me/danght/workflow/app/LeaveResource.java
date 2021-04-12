package me.danght.workflow.app;

import me.danght.workflow.app.dto.LeaveInfoDTO;
import me.danght.workflow.app.dto.UserInfoDTO;
import me.danght.workflow.app.service.LeaveInfoService;
import me.danght.workflow.app.service.UserInfoService;
import me.danght.workflow.common.api.form.ClientProcessService;
import me.danght.workflow.common.api.form.ClientTaskService;
import me.danght.workflow.common.constant.ParamType;
import me.danght.workflow.common.dataobject.ParmObject;
import org.apache.dubbo.config.annotation.DubboReference;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * 请假申请API
 * @author wenxiang
 * @author DangHT
 */
@Path("/leave")
@Produces(MediaType.APPLICATION_JSON)
public class LeaveResource {

    @Inject
    UserInfoService userInfoService;

    @Inject
    LeaveInfoService leaveInfoService;

    @DubboReference(check = false)
    ClientProcessService clientProcessService;

    @DubboReference(check = false)
    ClientTaskService clientTaskService;

    @POST
    @Path("addLeave")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addLeave(
            @QueryParam("uiId") String uiId,
            @QueryParam("durations") String durations,
            @QueryParam("pdId") String pdId) {
        LeaveInfoDTO leaveInfoDTO = new LeaveInfoDTO().setDurations(Integer.parseInt(durations))
                .setUiId(uiId);
        leaveInfoDTO = leaveInfoService.addLeave(leaveInfoDTO);
        UserInfoDTO userInfoDTO = userInfoService.getUserById(uiId);
        Map<String, ParmObject> requiredData = new HashMap<>();
        ParmObject parmObject = new ParmObject()
                .setPpType(ParamType.PARAM_TYPE_INT)
                .setVal(Integer.parseInt(durations));
        requiredData.put("durations",parmObject);
        clientProcessService.startProcess(pdId,userInfoDTO.getUiName() + "请假申请",userInfoDTO.getId(),leaveInfoDTO.getId(),requiredData);
        return "1";
    }

    @GET
    @Path("queryLeaveInfo")
    public LeaveInfoDTO queryLeaveInfo(@QueryParam("piBusinessKey") String piBusinessKey) {
        return leaveInfoService.getLeaveInfoById(piBusinessKey);
    }
}
