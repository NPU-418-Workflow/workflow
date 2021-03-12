package me.danght.workflow.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oilpeddler.wfengine.common.api.formservice.WfProcessParamsRelationService;
import com.oilpeddler.wfengine.common.api.scheduleservice.WfProcessDefinitionService;
import com.oilpeddler.wfengine.common.bo.WfProcessDefinitionBO;
import com.oilpeddler.wfengine.common.dto.WfProcessParamsRelationDTO;
import com.oilpeddler.wfengine.common.dto.WfProcessTemplateDTO;
import com.oilpeddler.wfengine.schedulecomponent.convert.WfProcessDefinitionConvert;
import com.oilpeddler.wfengine.schedulecomponent.dao.WfProcessDefinitionMapper;
import com.oilpeddler.wfengine.schedulecomponent.dao.WfProcessTemplateMapper;
import com.oilpeddler.wfengine.schedulecomponent.dao.redis.BpmnModelCacheDao;
import com.oilpeddler.wfengine.schedulecomponent.dataobject.WfProcessDefinitionDO;
import com.oilpeddler.wfengine.schedulecomponent.dataobject.WfProcessTemplateDO;
import com.oilpeddler.wfengine.schedulecomponent.element.BpmnModel;
import com.oilpeddler.wfengine.schedulecomponent.element.DataParam;
import com.oilpeddler.wfengine.schedulecomponent.element.StartEvent;
import com.oilpeddler.wfengine.schedulecomponent.element.UserTask;
import com.oilpeddler.wfengine.schedulecomponent.tools.BpmnXMLConvertUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@Service
public class WfProcessDefinitionServiceImpl implements WfProcessDefinitionService {
    @Autowired
    WfProcessDefinitionMapper wfProcessDefinitionMapper;

    @Autowired
    WfProcessTemplateMapper wfProcessTemplateMapper;

    @Reference
    WfProcessParamsRelationService wfProcessParamsRelationService;

    @Autowired
    BpmnModelCacheDao bpmnModelCacheDao;

    @Override
    public WfProcessDefinitionBO getWfProcessDefinitionById(String id) {
        WfProcessDefinitionDO wfProcessDefinitionDO = wfProcessDefinitionMapper.selectById(id);
        WfProcessDefinitionBO wfProcessDefinitionBO = WfProcessDefinitionConvert.INSTANCE.convertDOToBO(wfProcessDefinitionDO);
        WfProcessTemplateDO wfProcessTemplateDO = wfProcessTemplateMapper.selectById(wfProcessDefinitionDO.getPtId());
        wfProcessDefinitionBO.setPtContent(wfProcessTemplateDO.getPtContent());
        //wfProcessDefinitionBO.setBpmnModel(BpmnXMLConvertUtil.ConvertToBpmnModel(wfProcessDefinitionBO.getPtContent()));
        return wfProcessDefinitionBO;
    }

    @Override
    public WfProcessDefinitionBO generatePDFromTemplateFile(WfProcessTemplateDTO wfProcessTemplateDTO) {
        BpmnModel bpmnModel = BpmnXMLConvertUtil.ConvertToBpmnModel(wfProcessTemplateDTO.getPtContent());
        StartEvent startEvent = (StartEvent)bpmnModel.getProcess().getStartEvent();
        WfProcessDefinitionDO wfProcessDefinitionDO = new WfProcessDefinitionDO()
                .setPtId(wfProcessTemplateDTO.getId())
                .setPdName(bpmnModel.getName())
                .setPdNo(bpmnModel.getNo())
                .setStartForm(startEvent.getPageKey());
        wfProcessDefinitionDO.setCreatetime(new Date());
        wfProcessDefinitionDO.setUpdatetime(wfProcessDefinitionDO.getCreatetime());
        wfProcessDefinitionMapper.insert(wfProcessDefinitionDO);
        bpmnModelCacheDao.set(wfProcessDefinitionDO.getId(),bpmnModel);
        /**
         * 构造参数映射关系
         */
        List<UserTask> userTaskList =  bpmnModel.getProcess().getUserTaskList();
        for(UserTask userTask : userTaskList){
            if(userTask.getParamList() == null)
                continue;
            for(DataParam dataParam : userTask.getParamList()){
                WfProcessParamsRelationDTO wfProcessParamsRelationDTO = new WfProcessParamsRelationDTO()
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
            WfProcessParamsRelationDTO wfProcessParamsRelationDTO = new WfProcessParamsRelationDTO()
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
    public List<WfProcessDefinitionBO> queryDefinitionList() {
        QueryWrapper<WfProcessDefinitionDO> queryWrapper = new QueryWrapper<>();
        List<WfProcessDefinitionDO> wfProcessDefinitionDOList = wfProcessDefinitionMapper.selectList(queryWrapper);
        List<WfProcessDefinitionBO> wfProcessDefinitionBOList = new ArrayList<>();
        for(WfProcessDefinitionDO wfProcessDefinitionDO : wfProcessDefinitionDOList){
            wfProcessDefinitionBOList.add(WfProcessDefinitionConvert.INSTANCE.convertDOToBO(wfProcessDefinitionDO));
        }
        return wfProcessDefinitionBOList;
    }
}
