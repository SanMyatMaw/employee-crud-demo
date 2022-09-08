package com.employee.crud.demo.controller;



import com.employee.crud.demo.model.CommonResponseBody;
import com.employee.crud.demo.model.EmployeeRequestBody;
import com.employee.crud.demo.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static com.employee.crud.demo.constant.Enum.*;

@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    private final static Logger logger = LogManager.getLogger(EmployeeRestController.class);

    @PostMapping("/employees")
    public @ResponseBody ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeRequestBody employeeRequestBody){

        try{
            logger.info("Employee Create Request.");
            String responseText = employeeService.createEmployee(employeeRequestBody);
            // successful for creating employee
            logger.info("Successfully created user : " + employeeRequestBody.getFirstName());
            CommonResponseBody commonResponseBody = new CommonResponseBody();
            commonResponseBody.setId(responseText);
            commonResponseBody.setStatus(SUCCESS_CODE_200);
            commonResponseBody.setMessage(SUCCESS_MESSAGE);
            return new ResponseEntity<>(commonResponseBody, HttpStatus.OK);
        }
        catch (Exception e){

            logger.error("Exception happened while creating employee : " + e.getMessage());
            //Invalid Request Param
            if (e.getMessage().contains(INVALID_PARAM)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @GetMapping("/employees")
    public @ResponseBody ResponseEntity<?> getAllEmployees(){
        try{
            logger.info("Get All Employees Request.");
            List<EmployeeRequestBody> getEmployeesList = employeeService.getAllEmployees();
            //successful for get all employees
            logger.info("Employees List : " + getEmployeesList);
            return new ResponseEntity<>(getEmployeesList,HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Exception happened while getting all employees : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/employees/{id}")
    public @ResponseBody ResponseEntity<?> getEmplyeeById(@PathVariable("id") String employeeId){
        try{
            logger.info("Get Employee by Id Request.");
            EmployeeRequestBody getEmployee = employeeService.getEmployeeById(employeeId);
            //successful for get employee by Id
            logger.info("Get Employee by ID : " + employeeId);
            return new ResponseEntity<>(getEmployee,HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Exception happened while getting employee by Id : " + e.getMessage());
            if (e.getMessage().contains(RESOURCE_NOT_FOUND)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,RESOURCE_NOT_FOUND);
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @PutMapping("/employees/{id}")
    public @ResponseBody ResponseEntity<?> updateEmployee(@PathVariable("id") String employeeId, @Valid @RequestBody EmployeeRequestBody employeeRequestBody){

        try{
            logger.info("Employee Update Request.");
            String responseText = employeeService.updateEmployee(employeeId,employeeRequestBody);
            // successful for updating employee
            logger.info("Updated Employee : " + employeeRequestBody.getFirstName());
            CommonResponseBody commonResponseBody = new CommonResponseBody();
            commonResponseBody.setId(responseText);
            commonResponseBody.setStatus(SUCCESS_CODE_200);
            commonResponseBody.setMessage(SUCCESS_MESSAGE);
            return new ResponseEntity<>(commonResponseBody, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Exception happened while updating employee : " + e.getMessage());
            //Invalid Request Param
            if (e.getMessage().contains(INVALID_PARAM)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            //Resource Not Found
            else if (e.getMessage().contains(RESOURCE_NOT_FOUND)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,RESOURCE_NOT_FOUND);
            }
            //Resource Missmatched
            else if (e.getMessage().contains(RESOURCE_MISSMATCH)){
                throw new ResponseStatusException(HttpStatus.CONFLICT,RESOURCE_MISSMATCH);
            }
            //Internal Error
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @DeleteMapping("/employees/{id}")
    public @ResponseBody ResponseEntity<?> deleteEmployee(@PathVariable("id") String employeeId){
        try{
            logger.info("Delete Employee Request.");
             CommonResponseBody commonResponseBody= employeeService.deleteEmployee(employeeId);
             //successful for deleting  employee
             logger.info("Deleted Employee with Id : " + commonResponseBody.getId());
             return new ResponseEntity<>(commonResponseBody, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Exception happened while deleting employee : " + e.getMessage());
            if (e.getMessage().contains(RESOURCE_NOT_FOUND)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,RESOURCE_NOT_FOUND);
            }
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }

        }
    }
}
