package me.danght.workflow.scheduler.element;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DataParam implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String taskNo;
    protected String ppName;
    protected String ppType;
    protected String enginePpName;
}
