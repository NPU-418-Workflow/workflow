package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.WfElementDO;
import org.springframework.data.repository.CrudRepository;

public interface WfElementMapper extends CrudRepository<WfElementDO, String> {

}
