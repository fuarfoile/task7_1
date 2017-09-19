package com.boast.transferobject;

import java.util.List;

public class Employee extends Entity {

    public List<Task> tasks;

    private String name;
    private String surname;
    private String position;
    private int departmentNumber;

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return this.surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getPosition() { return this.position; }
    public void setPosition(String position) { this.position = position; }

    public int getDepartmentNumber() { return this.departmentNumber; }
    public void setDepartmentNumber(int departmentNumber) { this.departmentNumber = departmentNumber; }

    @Override
    public String toString() {
        return "[number = " + id + ", name = " + name + " " + surname + ", position = " + position + ", department number = " + departmentNumber + "]";
    }
}
