package me.danght.workflow.service;

import me.danght.workflow.dto.BusinessFormDTO;

public interface BusinessFormService {
    BusinessFormDTO selectById(String id);
}
