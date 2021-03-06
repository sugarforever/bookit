package com.wiysoft.mvc.m.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by weiliyang on 1/25/16.
 */
public class CreateBookableForm {

    @NotEmpty
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
