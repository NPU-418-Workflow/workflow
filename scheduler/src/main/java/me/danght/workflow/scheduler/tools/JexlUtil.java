package me.danght.workflow.scheduler.tools;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.util.Map;

public class JexlUtil {


    public static Boolean conditionIsMacth(String conditionExpression, Map<String, Object> requiredData){
        if(conditionExpression == null || conditionExpression.length() == 0)
            conditionExpression = "true";
        JexlContext jc = new MapContext();
        for(Map.Entry<String ,Object> entry : requiredData.entrySet()){
            jc.set(entry.getKey(), entry.getValue());
        }
        String expressionStr="answer = " + conditionExpression;
        Expression expression = new JexlEngine().createExpression(expressionStr);
        expression.evaluate(jc);
        Boolean isMatch = Boolean.valueOf(jc.get("answer").toString());
        return isMatch;
    }
}
