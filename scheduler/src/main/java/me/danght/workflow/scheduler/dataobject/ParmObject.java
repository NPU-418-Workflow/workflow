package me.danght.workflow.scheduler.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ParmObject implements Serializable {
    private static final long serialVersionUID=1L;
    String ppType;
    Object val;
}
