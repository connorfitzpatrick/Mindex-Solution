package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class) // or SpringRunner.class if you need Spring's context
@SpringBootTest
public class CompensationServiceImplTest {

    @InjectMocks
    private CompensationServiceImpl compensationService;

    @Mock
    private CompensationRepository compensationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void create() {
        Employee employee = new Employee("16a596ae-edd3-4847-99fe-c4518e82c86f", "John", "Lennon", "Development Manager", "Engineering", null);
        Compensation compensation = new Compensation(employee, 120000, LocalDate.of(2024, 8, 27));

        when(compensationRepository.save(compensation)).thenReturn(compensation);

        Compensation createdCompensation = compensationService.create(compensation);

        assertNotNull(createdCompensation);
        assertEquals(120000, createdCompensation.getSalary(), 0);
        assertEquals(LocalDate.of(2024, 8, 27), createdCompensation.getEffectiveDate());
    }

    @Test
    public void readByEmployeeId() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        Employee employee = new Employee(employeeId, "John", "Lennon", "Development Manager", "Engineering", null);
        Compensation compensation = new Compensation(employee, 120000, LocalDate.of(2024, 8, 27));

        when(compensationRepository.findByEmployee_EmployeeId(employeeId)).thenReturn(compensation);

        Compensation foundCompensation = compensationService.readByEmployeeId(employeeId);

        assertNotNull(foundCompensation);
        assertEquals(employeeId, foundCompensation.getEmployee().getEmployeeId());
        assertEquals(120000, foundCompensation.getSalary(), 0);
    }
}
