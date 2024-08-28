package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    @Autowired
    private CompensationRepository compensationRepository;

    // service method to create a new compensation
    @Override
    public Compensation create(Compensation compensation) {
        // save compensation to repository so that it persists
        return compensationRepository.save(compensation);
    }

    // service method to read or get a compensation based on employeeId
    @Override
    public Compensation readByEmployeeId(String employeeId) {
        Compensation compensation = compensationRepository.findByEmployee_EmployeeId(employeeId);

        // throw exception if compensation not found for employeeId
        if (compensation == null) {
            throw new RuntimeException("Compensation not found for id: " + employeeId);
        }
        return compensation;
    }
}
