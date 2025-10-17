package com.example.testui.untilities.formatter;

import com.example.testui.model.Department;

public class DepartmentFormatter {
    public static Department format(Department department) {
        if (department == null) {
            department = new Department();
        }
        if  (department.getCode() == null) department.setCode("-");
        if (department.getDescription() == null) department.setDescription("-");
        if (department.getName() == null) department.setName("-");
        department.setFaculties(FacultiesFormatter.format(department.getFaculties()));
        return department;
    }
}
