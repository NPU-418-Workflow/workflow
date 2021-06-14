package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.UserInfoDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfoDO, String> {
    Optional<UserInfoDO> findByUiName(String uiName);
}
