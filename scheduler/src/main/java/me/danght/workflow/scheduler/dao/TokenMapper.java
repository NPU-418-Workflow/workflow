package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenMapper extends CrudRepository<Token, String> {
    void decrementChildNum(String id);
    Optional<Token> findByPiIdAndElementNo(String piId, String elementNo);
    Optional<Token> findByPiIdAndPdIdAndElementNo(String piId, String pdId, String elementNo);
    Iterable<Token> findAllByParentIdAndIdIsNot(String parentId, String id);
}
