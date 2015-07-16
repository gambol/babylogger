package com.rorlig.babylog.model.diaper;

/**
 * @author gaurav gupta
 * Incident Type
 */
public enum DiaperIncident {
    NONE ("未发生"), NO_DIAPER("没有尿片"), DIAPER_LEAK ("尿片漏了");

    private final String value;

    DiaperIncident(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
