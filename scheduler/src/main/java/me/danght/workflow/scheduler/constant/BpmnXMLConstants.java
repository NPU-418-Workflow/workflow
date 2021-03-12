package me.danght.workflow.scheduler.constant;

public class BpmnXMLConstants {
    //元素
    public static final String ELEMENT_PROCESS = "process";
    public static final String ELEMENT_TASK_USER = "userTask";
    public static final String ELEMENT_SEQUENCE_FLOW = "sequenceFlow";
    public static final String ELEMENT_GATEWAY_EXCLUSIVE = "exclusiveGateway";
    public static final String ELEMENT_GATEWAY_PARALLEL = "parallelGateway";
    public static final String ELEMENT_EVENT_START = "startEvent";
    public static final String ELEMENT_EVENT_END = "endEvent";

    //公共属性
    public static final String ATTRIBUTE_NO = "no";
    public static final String ATTRIBUTE_NAME = "name";
    //活动属性
    public static final String ATTRIBUTE_TASK_USER_ASSIGNEETYPE = "assigneeType";
    public static final String ATTRIBUTE_TASK_USER_ASSIGNEE = "assignee";
    public static final String ATTRIBUTE_TASK_USER_TYPE = "taskType";
    public static final String ATTRIBUTE_TASK_USER_PAGEKEY = "pageKey";
    public static final String ATTRIBUTE_SEQUENCE_FLOW_CONDITION = "conditionExpression";
    public static final String ATTRIBUTE_SEQUENCE_FLOW_SOURCE_REF = "sourceRef";
    public static final String ATTRIBUTE_SEQUENCE_FLOW_TARGET_REF = "targetRef";
    public static final String ATTRIBUTE_SEQUENCE_FLOW_PARAMLIST = "paramList";


    //definitions根节点属性
    //public static final String ATTRIBUTE_DEFINITIONS_ID = "definitionsId";
}
