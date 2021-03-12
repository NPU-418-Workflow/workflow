package me.danght.workflow.scheduler.service;

import me.danght.workflow.scheduler.element.Process;
import me.danght.workflow.scheduler.dataobject.Token;

public interface TokenService {
    Token recoverTokens(String piId, String pdId, String elementNo, Process process);

    Token getCurrentToken(String piId,String elementNo, Process process);
    Token getFromCache(String id);
    void setToCache(Token token);
}
