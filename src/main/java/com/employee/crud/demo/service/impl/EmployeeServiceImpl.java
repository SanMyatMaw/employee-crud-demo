package com.employee.crud.demo.service.impl;

import com.employee.crud.demo.model.CommonResponseBody;
import com.employee.crud.demo.model.EmployeeDao;
import com.employee.crud.demo.model.EmployeeRequestBody;
import com.employee.crud.demo.repository.EmployeeRepository;
import com.employee.crud.demo.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static com.employee.crud.demo.constant.Enum.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final static Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

    @Override
    public String createEmployee(EmployeeRequestBody employeeRequestBody){
            if (employeeRequestBody.getFirstName().isEmpty() || employeeRequestBody.getEmailId().isEmpty()) {
                //throw Bad Request
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_PARAM);
            }
            else
                {
                  EmployeeDao employeeDao = new EmployeeDao();
                  employeeDao.setId(employeeRequestBody.getId());
                  employeeDao.setFirstName(employeeRequestBody.getFirstName());
                  employeeDao.setLastName(employeeRequestBody.getLastName());
                  employeeDao.setEmailId(employeeRequestBody.getEmailId());
                  employeeDao.setCreatedDate(LocalDateTime.now());
                  employeeDao.setLastModifiedDate(LocalDateTime.now());
                  return employeeRepository.save(employeeDao).getId();
            }
    }

    @Override
    public List<EmployeeRequestBody>getAllEmployees(){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        //To response employeeBody List
        List<EmployeeRequestBody> employeeList = new ArrayList<>();
        List<EmployeeDao> employeeDaoList = employeeRepository.findAll();
        if (!employeeDaoList.isEmpty()){
            for (EmployeeDao obj : employeeDaoList){
                EmployeeRequestBody employees = modelMapper.map(obj,EmployeeRequestBody.class);
                employeeList.add(employees);
            }
        }
        return employeeList;
    }

    @Override
    public EmployeeRequestBody getEmployeeById(String employeeId){

        EmployeeRequestBody employeeBody = new EmployeeRequestBody();
        Optional<EmployeeDao> employeeDao = employeeRepository.findById(employeeId);
            if (!employeeDao.isEmpty()){
                employeeBody.setId(employeeDao.get().getId());
                employeeBody.setFirstName(employeeDao.get().getFirstName());
                employeeBody.setLastName(employeeDao.get().getLastName());
                employeeBody.setEmailId(employeeDao.get().getEmailId());
            }
            else {
                logger.error("Employee not found for this id : " + employeeId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,RESOURCE_NOT_FOUND);
            }
        return employeeBody;
    }

    @Override
    public String updateEmployee(String employeeId,EmployeeRequestBody employeeRequestBody){

        if (employeeRequestBody.getFirstName().isEmpty() || employeeRequestBody.getEmailId().isEmpty()){
            //throw Bad Request
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,INVALID_PARAM);
        }
        //check employeeId whether exist or not
        Optional<EmployeeDao> employeeDao = employeeRepository.findById(employeeId);
        logger.info("Existing Employee check by Id : " + employeeDao);
        if (!employeeDao.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,RESOURCE_NOT_FOUND);
        }
        else if (employeeDao.isPresent() && !employeeDao.get().getId().equals(employeeRequestBody.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,RESOURCE_MISSMATCH);
        }
        else {
            EmployeeDao updateEmployeeDao = new EmployeeDao();
            updateEmployeeDao.setId(employeeRequestBody.getId());
            updateEmployeeDao.setFirstName(employeeRequestBody.getFirstName());
            updateEmployeeDao.setLastName(employeeRequestBody.getLastName());
            updateEmployeeDao.setEmailId(employeeRequestBody.getEmailId());
            updateEmployeeDao.setCreatedDate(LocalDateTime.now());
            updateEmployeeDao.setLastModifiedDate(LocalDateTime.now());
            return employeeRepository.save(updateEmployeeDao).getId();
        }
    }

    @Override
    public CommonResponseBody deleteEmployee(String employeeId){

        CommonResponseBody commonResponseBody = new CommonResponseBody();
        //check employeeId whether exist or not
        Optional<EmployeeDao> deleteEmployeeDao = employeeRepository.findById(employeeId);
        if (!deleteEmployeeDao.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,RESOURCE_NOT_FOUND);
        }
        else {
            employeeRepository.deleteById(deleteEmployeeDao.get().getId());
            commonResponseBody.setId(employeeId);
            commonResponseBody.setStatus(SUCCESS_CODE_200);
            commonResponseBody.setMessage(SUCCESS_MESSAGE);
        }
        return commonResponseBody;
    }
}
