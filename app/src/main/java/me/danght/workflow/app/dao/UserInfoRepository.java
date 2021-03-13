package me.danght.workflow.app.dao;

import me.danght.workflow.app.dataobject.UserInfoDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfoDO, String> {
    Optional<UserInfoDO> findByUiName(String uiName);
}
