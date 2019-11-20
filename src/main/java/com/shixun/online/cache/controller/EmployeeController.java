package com.shixun.online.cache.controller;

import com.shixun.online.cache.pojo.Employee;
import com.shixun.online.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
   private EmployeeService employeeService;

    @GetMapping("/getEmployee/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmp(id);
        return employee;
    }

    @GetMapping("/updateEmployee")
    public Employee update( Employee employee){

        Employee emp = employeeService.updateEmp(employee);

        return emp;
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmp(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/{lastName}")
    public Employee getEmployeeByLastName(@PathVariable("lastName") String lastName){
        return employeeService.getEmployeeByLastName(lastName);
    }
}
