package me.danght.workflow.scheduler.service.impl;

import me.danght.workflow.scheduler.dao.TokenRepository;
import me.danght.workflow.scheduler.dao.redis.TokenCacheDao;
import me.danght.workflow.scheduler.dataobject.Token;
import me.danght.workflow.scheduler.element.UserTask;
import me.danght.workflow.scheduler.element.Process;
import me.danght.workflow.scheduler.service.TokenService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import java.util.List;

/**
 * 令牌服务
 *
 * @author wenxiang
 * @author DangHT
 */
@ApplicationScoped
@ActivateRequestContext
public class TokenServiceImpl implements TokenService {

    @Inject
    TokenRepository tokenRepository;

    @Inject
    TokenCacheDao tokenCacheDao;

    @Override
    public Token getCurrentToken(String piId, String elementNo, Process process){
        Token currentToken = tokenRepository.findByPiIdAndElementNo(piId, elementNo).orElse(null);
        if (currentToken != null) {
            currentToken.setCurrentNode(findUserTaskByNo(currentToken.getElementNo(), process));
            if (currentToken.getCurrentNode() == null && process.getStartEvent().getNo().equals(currentToken.getElementNo())) {
                currentToken.setCurrentNode(process.getStartEvent());
            }
        }
        return currentToken;
    }

    @Override
    public Token recoverTokens(String piId, String pdId, String elementNo, Process process) {
        //目前认为，一个活动（只限于UserTask，gateway啥的不算）上只可能有一个token
        Token currentToken = tokenRepository.findByPiIdAndPdIdAndElementNo(piId, pdId, elementNo).orElse(null);
        if (currentToken == null) return null;
        Token parent = currentToken;
        while (!"0".equals(parent.getParentId())){
            List<Token> concurrentTokens = tokenRepository.findAllByParentIdAndIdIsNot(parent.getParentId(), parent.getId());
            concurrentTokens.add(parent);
            parent = tokenRepository.findById(parent.getParentId()).orElse(null);
            if (parent == null) return null;
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
