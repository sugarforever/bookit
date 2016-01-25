package com.wiysoft.mvc.m.forms;

/**
 * Created by weiliyang on 1/25/16.
 */
public class CreateBookableForm {

    private String name;
    private Integer quantity;
    private String unit;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }
}
