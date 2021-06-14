package me.danght.workflow.scheduler;

import me.danght.workflow.scheduler.constant.ParamType;
import me.danght.workflow.scheduler.dataobject.ParmObject;
import me.danght.workflow.scheduler.dto.LeaveInfoDTO;
import me.danght.workflow.scheduler.dto.UserInfoDTO;
import me.danght.workflow.scheduler.service.ClientProcessService;
import me.danght.workflow.scheduler.service.LeaveInfoService;
import me.danght.workflow.scheduler.service.UserInfoService;
import org.jboss.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(UserResource.class);

    @Inject
    UserInfoService userInfoService;

    @Inject
    LeaveInfoService leaveInfoService;

    @Inject
    ClientProcessService clientProcessService;

    @POST
    @Path("addLeave")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addLeave(
            @QueryParam("uiId") String uiId,
            @QueryParam("durations") String durations,
            @QueryParam("pdId") String pdId) {
        LOGGER.info("用户(id=" + uiId + ")提交请假申请(id=" + pdId + "), 请假时长: " + durations + "天");
        LeaveInfoDTO leaveInfoDTO = new LeaveInfoDTO().setDurations(Integer.parseInt(durations))
                .setUiId(uiId);
        leaveInfoDTO = leaveInfoService.addLeave(leaveInfoDTO);
        if (leaveInfoDTO != null) {
            LOGGER.info("用户(id=" + uiId + ")的请假申请(id=" + pdId + ")成功插入数据库");
        } else {
            LOGGER.error("用户(id=" + uiId + ")的请假申请(id=" + pdId + ")插入数据库失败");
        }
        UserInfoDTO userInfoDTO = userInfoService.getUserById(uiId);
        Map<String, ParmObject> requiredData = new HashMap<>();
        ParmObject parmObject = new ParmObject()
                .setPpType(ParamType.PARAM_TYPE_INT)
                .setVal(Integer.parseInt(durations));
        requiredData.put("durations",parmObject);
        if (clientProcessService.startProcess(
                pdId,
                userInfoDTO.getUiName() + "请假申请",
                userInfoDTO.getId(),
                leaveInfoDTO.getId(),
                requiredData)) {
            LOGGER.info("开启请假流程(id=" + leaveInfoDTO.getId() + ")成功");
        } else {
            LOGGER.error("开启请假流程(id=" + leaveInfoDTO.getId() + ")失败");
        }
        return "1";
    }

    @GET
    @Path("queryLeaveInfo")
    public LeaveInfoDTO queryLeaveInfo(@QueryParam("piBusinessKey") String piBusinessKey) {
        LOGGER.info("查询请假信息(piBusinessKey=" + piBusinessKey + ")");
        return leaveInfoService.getLeaveInfoById(piBusinessKey);
    }
}
