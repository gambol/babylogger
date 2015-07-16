package com.rorlig.babylog.model.diaper;

/**
* @author gaurav gupta
 * Poop Texture
 */
public enum DiaperChangeTextureType {
    LOOSE("水状"), SEEDY("糊状"), CHUNKY("比较干"), HARD("非常干");

    private final String value;

    DiaperChangeTextureType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
