package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.common.api.form.ProcessParamsRelationService;
import me.danght.workflow.common.api.schduler.ProcessDefinitionService;
import me.danght.workflow.common.bo.ProcessDefinitionBO;
import me.danght.workflow.common.dto.ProcessParamsRelationDTO;
import me.danght.workflow.common.dto.ProcessTemplateDTO;
import me.danght.workflow.scheduler.convert.ProcessDefinitionConvert;
import me.danght.workflow.scheduler.dao.ProcessDefinitionRepository;
import me.danght.workflow.scheduler.dao.ProcessTemplateRepository;
import me.danght.workflow.scheduler.dao.redis.BpmnModelCacheDao;
import me.danght.workflow.scheduler.dataobject.ProcessDefinitionDO;
import me.danght.workflow.scheduler.dataobject.ProcessTemplateDO;
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
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

    @Inject
    ProcessDefinitionRepository processDefinitionRepository;

    @Inject
    ProcessTemplateRepository processTemplateRepository;

    @DubboReference
    ProcessParamsRelationService processParamsRelationService;

    @Inject
    BpmnModelCacheDao bpmnModelCacheDao;

    @Override
    public ProcessDefinitionBO getWfProcessDefinitionById(String id) {
        ProcessDefinitionDO processDefinitionDO = processDefinitionRepository.findById(id).orElse(null);
        if (processDefinitionDO == null) return null;
        ProcessDefinitionBO processDefinitionBO = ProcessDefinitionConvert.INSTANCE.convertDOToBO(processDefinitionDO);
        ProcessTemplateDO processTemplateDO = processTemplateRepository.findById(processDefinitionDO.getPtId()).orElse(null);
        if (processTemplateDO == null) return null;
        processDefinitionBO.setPtContent(processTemplateDO.getPtContent());
        return processDefinitionBO;
    }

    @Override
    public ProcessDefinitionBO generatePDFromTemplateFile(ProcessTemplateDTO processTemplateDTO) {
        BpmnModel bpmnModel = BpmnXMLConvertUtil.ConvertToBpmnModel(processTemplateDTO.getPtContent());
        StartEvent startEvent = (StartEvent) bpmnModel.getProcess().getStartEvent();
        ProcessDefinitionDO processDefinitionDO = new ProcessDefinitionDO()
                .setPtId(processTemplateDTO.getId())
                .setPdName(bpmnModel.getName())
                .setPdNo(bpmnModel.getNo())
                .setStartForm(startEvent.getPageKey());
        processDefinitionDO.setCreateTime(new Date());
        processDefinitionDO.setUpdateTime(processDefinitionDO.getCreateTime());
        processDefinitionRepository.save(processDefinitionDO);
        bpmnModelCacheDao.set(processDefinitionDO.getId(),bpmnModel);

        List<UserTask> userTaskList =  bpmnModel.getProcess().getUserTaskList();
        for(UserTask userTask : userTaskList){
            if(userTask.getParamList() == null) {
                continue;
            }
            for(DataParam dataParam : userTask.getParamList()){
                ProcessParamsRelationDTO processParamsRelationDTO = new ProcessParamsRelationDTO()
                        .setPpName(dataParam.getPpName())
                        .setPpLevel("02")
                        .setPpType(dataParam.getPpType())
                        .setPdId(processDefinitionDO.getId())
                        .setTaskNo(dataParam.getTaskNo())
                        .setEnginePpName(dataParam.getEnginePpName());
                processParamsRelationDTO.setCreateTime(new Date());
                processParamsRelationDTO.setUpdateTime(processParamsRelationDTO.getCreateTime());
                processParamsRelationService.save(processParamsRelationDTO);
            }
        }

        for(DataParam dataParam : ((StartEvent) bpmnModel.getProcess().getStartEvent()).getParamList()){
            ProcessParamsRelationDTO processParamsRelationDTO = new ProcessParamsRelationDTO()
                    .setPpName(dataParam.getPpName())
                    .setPpLevel("02")
                    .setPpType(dataParam.getPpType())
                    .setPdId(processDefinitionDO.getId())
                    .setTaskNo(dataParam.getTaskNo())
                    .setEnginePpName(dataParam.getEnginePpName());
            processParamsRelationDTO.setCreateTime(new Date());
            processParamsRelationDTO.setUpdateTime(processParamsRelationDTO.getCreateTime());
            processParamsRelationService.save(processParamsRelationDTO);
        }

        return ProcessDefinitionConvert.INSTANCE.convertDOToBO(processDefinitionDO);
    }


    @Override
    public List<ProcessDefinitionBO> queryDefinitionList() {
        List<ProcessDefinitionDO> processDefinitionDOList = (List<ProcessDefinitionDO>) processDefinitionRepository.findAll();
        List<ProcessDefinitionBO> processDefinitionBOList = new ArrayList<>();
        for(ProcessDefinitionDO processDefinitionDO : processDefinitionDOList){
            processDefinitionBOList.add(ProcessDefinitionConvert.INSTANCE.convertDOToBO(processDefinitionDO));
        }
        return processDefinitionBOList;
    }
}
