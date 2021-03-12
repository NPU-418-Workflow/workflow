package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.api.form.ProcessParamsRelationService;
import me.danght.workflow.common.api.schduler.ProcessDefinitionService;
import me.danght.workflow.common.bo.ProcessDefinitionBO;
import me.danght.workflow.common.dto.ProcessParamsRelationDTO;
import me.danght.workflow.common.dto.ProcessTemplateDTO;
import me.danght.workflow.scheduler.convert.WfProcessDefinitionConvert;
import me.danght.workflow.scheduler.dao.WfProcessDefinitionMapper;
import me.danght.workflow.scheduler.dao.WfProcessTemplateMapper;
import me.danght.workflow.scheduler.dao.redis.BpmnModelCacheDao;
import me.danght.workflow.scheduler.dataobject.WfProcessDefinitionDO;
import me.danght.workflow.scheduler.dataobject.WfProcessTemplateDO;
import me.danght.workflow.scheduler.element.BpmnModel;
import me.danght.workflow.scheduler.element.DataParam;
import me.danght.workflow.scheduler.element.StartEvent;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.tools.BpmnXMLConvertUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = ProcessDefinitionService.class)
@Singleton
public class WfProcessDefinitionServiceImpl implements ProcessDefinitionService {

    @Inject
    WfProcessDefinitionMapper wfProcessDefinitionMapper;

    @Inject
    WfProcessTemplateMapper wfProcessTemplateMapper;

    @DubboReference
    ProcessParamsRelationService wfProcessParamsRelationService;

    @Inject
    BpmnModelCacheDao bpmnModelCacheDao;

    @Override
    public ProcessDefinitionBO getWfProcessDefinitionById(String id) {
        WfProcessDefinitionDO wfProcessDefinitionDO = wfProcessDefinitionMapper.findById(id).get();
        ProcessDefinitionBO wfProcessDefinitionBO = WfProcessDefinitionConvert.INSTANCE.convertDOToBO(wfProcessDefinitionDO);
        WfProcessTemplateDO wfProcessTemplateDO = wfProcessTemplateMapper.findById(wfProcessDefinitionDO.getPtId()).get();
        wfProcessDefinitionBO.setPtContent(wfProcessTemplateDO.getPtContent());
        return wfProcessDefinitionBO;
    }

    @Override
    public ProcessDefinitionBO generatePDFromTemplateFile(ProcessTemplateDTO wfProcessTemplateDTO) {
        BpmnModel bpmnModel = BpmnXMLConvertUtil.ConvertToBpmnModel(wfProcessTemplateDTO.getPtContent());
        StartEvent startEvent = (StartEvent) bpmnModel.getProcess().getStartEvent();
        WfProcessDefinitionDO wfProcessDefinitionDO = new WfProcessDefinitionDO()
                .setPtId(wfProcessTemplateDTO.getId())
                .setPdName(bpmnModel.getName())
                .setPdNo(bpmnModel.getNo())
                .setStartForm(startEvent.getPageKey());
        wfProcessDefinitionDO.setCreatetime(new Date());
        wfProcessDefinitionDO.setUpdatetime(wfProcessDefinitionDO.getCreatetime());
        wfProcessDefinitionMapper.save(wfProcessDefinitionDO);
        bpmnModelCacheDao.set(wfProcessDefinitionDO.getId(),bpmnModel);

        List<UserTask> userTaskList =  bpmnModel.getProcess().getUserTaskList();
        for(UserTask userTask : userTaskList){
            if(userTask.getParamList() == null) {
                continue;
            }
            for(DataParam dataParam : userTask.getParamList()){
                ProcessParamsRelationDTO wfProcessParamsRelationDTO = new ProcessParamsRelationDTO()
                        .setPpName(dataParam.getPpName())
                        .setPpLevel("02")
                        .setPpType(dataParam.getPpType())
                        .setPdId(wfProcessDefinitionDO.getId())
                        .setTaskNo(dataParam.getTaskNo())
                        .setEnginePpName(dataParam.getEnginePpName());
                wfProcessParamsRelationDTO.setCreatetime(new Date());
                wfProcessParamsRelationDTO.setUpdatetime(wfProcessParamsRelationDTO.getCreatetime());
                wfProcessParamsRelationService.save(wfProcessParamsRelationDTO);
            }
        }

        for(DataParam dataParam : ((StartEvent) bpmnModel.getProcess().getStartEvent()).getParamList()){
            ProcessParamsRelationDTO wfProcessParamsRelationDTO = new ProcessParamsRelationDTO()
                    .setPpName(dataParam.getPpName())
                    .setPpLevel("02")
                    .setPpType(dataParam.getPpType())
                    .setPdId(wfProcessDefinitionDO.getId())
                    .setTaskNo(dataParam.getTaskNo())
                    .setEnginePpName(dataParam.getEnginePpName());
            wfProcessParamsRelationDTO.setCreatetime(new Date());
            wfProcessParamsRelationDTO.setUpdatetime(wfProcessParamsRelationDTO.getCreatetime());
            wfProcessParamsRelationService.save(wfProcessParamsRelationDTO);
        }

        return WfProcessDefinitionConvert.INSTANCE.convertDOToBO(wfProcessDefinitionDO);
    }


    @Override
    public List<ProcessDefinitionBO> queryDefinitionList() {
        List<WfProcessDefinitionDO> wfProcessDefinitionDOList = (List<WfProcessDefinitionDO>) wfProcessDefinitionMapper.findAll();
        List<ProcessDefinitionBO> wfProcessDefinitionBOList = new ArrayList<>();
        for(WfProcessDefinitionDO wfProcessDefinitionDO : wfProcessDefinitionDOList){
            wfProcessDefinitionBOList.add(WfProcessDefinitionConvert.INSTANCE.convertDOToBO(wfProcessDefinitionDO));
        }
        return wfProcessDefinitionBOList;
    }
}
