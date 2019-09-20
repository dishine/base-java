package com.shinedi.javabase.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author D-S
 * Created on 2019/9/20 10:04 上午
 */
@Getter
@Setter
public class Base {

    private String string;
    private boolean aBoolean;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return string.equals(base.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    @Override
    public String toString() {
        return "Base{" +
                "string='" + string + '\'' +
                ", aBoolean=" + aBoolean +
                '}';
    }

    public Base() {
    }

    public Base(String string, boolean aBoolean) {
        this.string = string;
        this.aBoolean = aBoolean;
    }
}
