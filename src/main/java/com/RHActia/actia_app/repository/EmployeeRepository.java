package com.RHActia.actia_app.repository;

import com.RHActia.actia_app.model.Employee;
import com.RHActia.actia_app.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

        Employee  findByFirstname(String firstname);

        // Search by gender
        List<Employee> findByGender(Gender gender);

        // Search by team


        // Search by birthdate range
        //List<Employee> findByBirthDateBetween(Date  startDate, Date  endDate);
        Employee findByEmail(String email);


}
