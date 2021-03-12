package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.ElementDO;
import org.springframework.data.repository.CrudRepository;

public interface ElementRepository extends CrudRepository<ElementDO, String> {

}
