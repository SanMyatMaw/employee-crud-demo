package com.employee.crud.demo.service;

import com.employee.crud.demo.model.CommonResponseBody;
import com.employee.crud.demo.model.EmployeeRequestBody;


import java.util.List;

public interface EmployeeService {

    String createEmployee(EmployeeRequestBody employeeRequestBody);

    List<EmployeeRequestBody>getAllEmployees();

    EmployeeRequestBody getEmployeeById(String employeeId);

    String updateEmployee(String employeeId, EmployeeRequestBody employeeRequestBody);

    CommonResponseBody deleteEmployee (String employeeId);
}
