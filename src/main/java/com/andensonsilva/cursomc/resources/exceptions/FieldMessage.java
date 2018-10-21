package com.andensonsilva.cursomc.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 5236520270538956327L;

    private String fieldName;
    private String nessage;

    public FieldMessage(String fieldName, String nessage) {
        this.fieldName = fieldName;
        this.nessage = nessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getNessage() {
        return nessage;
    }

    public void setNessage(String nessage) {
        this.nessage = nessage;
    }
}
