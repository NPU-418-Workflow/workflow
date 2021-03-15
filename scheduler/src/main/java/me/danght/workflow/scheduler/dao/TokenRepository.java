package me.danght.workflow.scheduler.dao;

import me.danght.workflow.scheduler.dataobject.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findByPiIdAndElementNo(String piId, String elementNo);

    Optional<Token> findByPiIdAndPdIdAndElementNo(String piId, String pdId, String elementNo);

    List<Token> findAllByParentIdAndIdIsNot(String parentId, String id);
}
