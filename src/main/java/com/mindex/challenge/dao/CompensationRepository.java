package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    // Custom query for finding an employee by the employeeId
    Compensation findByEmployee_EmployeeId(String employeeId);
}
