package org.n3r.web.common;

public enum PageRowsEnum {

    SMALL(5),
    NORMAL(10),
    BIG(20);

    private int value;

    private PageRowsEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
