package com.boast.transferobject;

import java.io.Serializable;

public class Entity implements Serializable {
    protected int id = -1;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
}
