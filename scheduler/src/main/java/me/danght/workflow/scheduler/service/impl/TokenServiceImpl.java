package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.dao.TokenMapper;
import me.danght.workflow.scheduler.dao.redis.TokenCacheDao;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.element.Process;
import me.danght.workflow.scheduler.service.TokenService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TokenServiceImpl implements TokenService {

    @Inject
    TokenMapper tokenMapper;

    @Inject
    TokenCacheDao tokenCacheDao;

    @Override
    public Token getCurrentToken(String piId, String elementNo, Process process){
        Token currentToken = tokenMapper.findByPiIdAndElementNo(piId, elementNo).get();
        currentToken.setCurrentNode(findUserTaskByNo(currentToken.getElementNo(),process));
        if(currentToken.getCurrentNode() == null && process.getStartEvent().getNo().equals(currentToken.getElementNo()))
            currentToken.setCurrentNode(process.getStartEvent());
        return currentToken;
    }

    @Override
    public Token recoverTokens(String piId, String pdId, String elementNo, Process process) {
        //目前认为，一个活动（只限于UserTask，gateway啥的不算）上只可能有一个token
        Token currentToken = tokenMapper.findByPiIdAndPdIdAndElementNo(piId, pdId, elementNo).get();
        Token parent = currentToken;
        while (!"0".equals(parent.getParentId())){
            List<Token> concurrentTokens = (List<Token>) tokenMapper.findAllByParentIdAndIdIsNot(parent.getParentId(), parent.getId());
            concurrentTokens.add(parent);
            parent = tokenMapper.findById(parent.getParentId()).get();
            parent.setChildren(concurrentTokens);
            for(Token cToken : concurrentTokens){
                cToken.setParent(parent);
                cToken.setCurrentNode(findUserTaskByNo(cToken.getElementNo(),process));
            }
        }
        parent.setCurrentNode(findUserTaskByNo(parent.getElementNo(),process));
        return currentToken;
    }

    @Override
    public Token getFromCache(String id) {
        return tokenCacheDao.get(id);
    }

    @Override
    public void setToCache(Token token) {
        tokenCacheDao.set(token.getId(),token);
    }

    public UserTask findUserTaskByNo(String no, Process process) {
        for(UserTask userTask : process.getUserTaskList()){
            if(userTask.getNo().equals(no))
                return userTask;
        }
        return null;
    }
}
