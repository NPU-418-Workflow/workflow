package me.danght.workflow.form.service;

import me.danght.workflow.form.dto.BusinessFormDTO;

public interface BusinessFormService {
    BusinessFormDTO selectById(String id);
}
