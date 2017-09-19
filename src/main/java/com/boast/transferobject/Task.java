package com.boast.transferobject;

public class Task extends Entity {

    private String description = null;
    private int employeesNumber = 0;

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public int getEmployeesNumber() { return this.employeesNumber; }
    public void setEmployeesNumber(int employeesNumber) { this.employeesNumber = employeesNumber; }

    @Override
    public String toString() {
        return "[number = " + id +
                ", description = " + description +
                ", employeesNumber = " + employeesNumber + "]";
    }
}
