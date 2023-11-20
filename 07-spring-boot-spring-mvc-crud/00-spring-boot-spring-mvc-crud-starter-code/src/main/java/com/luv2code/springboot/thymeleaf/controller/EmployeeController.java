package com.luv2code.springboot.thymeleaf.controller;

import com.luv2code.springboot.thymeleaf.entity.Employee;
import com.luv2code.springboot.thymeleaf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    //add mapping for listing the employees

    @GetMapping("list")
    public String listEmployees(Model model){
        // get employees from db
        List<Employee> employeeList = employeeService.findAll();

        // add the data to the Model
        model.addAttribute("employees",employeeList);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "/employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee")Employee employee){
        //save employee
        employeeService.save(employee);
        //use redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId")int theId,Model model){

        Employee employee = employeeService.findById(theId);

        model.addAttribute("employee",employee);

        return "employees/employee-form";

    }
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId")int theId){
        employeeService.deleteById(theId);
        return "redirect:/employees/list";
    }

}
