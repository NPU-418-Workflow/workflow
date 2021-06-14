package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.dto.BusinessFormDTO;

public interface BusinessFormService {
    BusinessFormDTO selectById(String id);
}
