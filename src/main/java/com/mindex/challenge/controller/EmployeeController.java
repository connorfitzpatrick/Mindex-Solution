package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    CompensationService compensationService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee get request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    // endpoint for getting the reporting structure for some employeeId
    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure getReportingStructure(@PathVariable String id) {
        LOG.debug("Received ReportingStructure get request for id [{}]", id);
        Employee employee = employeeService.read(id);

        int numberOfReports = calculateNumberOfReports(employee);
        LOG.debug("Number of reports for id [{}] is [{}]", id ,numberOfReports);
        return new ReportingStructure(employee, numberOfReports);
    }

    // helper function that recursively calculates the number of reports.
    // I'm assuming we do not need to handle possible cyclic dependencies
    private int calculateNumberOfReports(Employee employee) {
        int count = 0;
        if (employee.getDirectReports() != null) {
        for (Employee e : employee.getDirectReports()) {
            count += (1 + calculateNumberOfReports(employeeService.read(e.getEmployeeId())));
        }}
        return count;
    }

    // endpoint to create Compensation
    @PostMapping("/compensation")
    public Compensation createCompensation(@RequestBody Compensation compensation) {
        LOG.debug("Received Compensation create request for id [{}]", compensation.getEmployee().getEmployeeId());
        return compensationService.create(compensation);
    }

    // endpoint to read Compensation by employeeId
    @GetMapping("/compensation/{employeeId}")
    public Compensation getCompensation(@PathVariable String employeeId) {
        LOG.debug("Received Compensation get request for id [{}]", employeeId);
        return compensationService.readByEmployeeId(employeeId);
    }

}
