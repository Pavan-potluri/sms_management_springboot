package com.pod5.sms_management.controller;

import java.util.List;

import com.pod5.sms_management.exception.CurrentUserException;
import com.pod5.sms_management.model.CurrentUserSession;
import com.pod5.sms_management.model.LogIn;
import com.pod5.sms_management.model.SmsEmployee;
import com.pod5.sms_management.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsManagementController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/create")
    public ResponseEntity<String> registerEmployee(@RequestBody SmsEmployee smsEmployee) {

        try {
            smsService.saveEmployee(smsEmployee);
            return new ResponseEntity<>("Employee Created successfully", HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to create employee";
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Message", errorMessage); // You can set a custom header for the message if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

    }

    @GetMapping("/display")
    public ResponseEntity<List<SmsEmployee>> getAllEmployees() {
        List<SmsEmployee> employees = smsService.getAllEmployees();
        if (employees.isEmpty()) {
            String errorMessage = "Employee list is empty right now";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", errorMessage); // You can set a custom header for the message if needed
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).build();
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/display/{Id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("Id") int Id) {
        SmsEmployee smsEmployee = smsService.getById(Id);
        if (smsEmployee != null) {
            return new ResponseEntity<>(smsEmployee, HttpStatus.OK);
        }
        String message = "Employee with ID: " + Id + " not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @GetMapping("/display/user")
    public ResponseEntity<?> getUserByName(@RequestParam(name="firstName") String firstName, @RequestParam(name="lastName") String lastName){

        if(lastName.equals("")) {
            if(smsService.getByFirstName(firstName) == null)
            {
                String message = "Employee with firstName: " + firstName + " not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }
            else return new ResponseEntity<>(smsService.getByFirstName(firstName), HttpStatus.OK);
        }
        else if(firstName.equals("")) {
            if(smsService.getByLastName(lastName) == null)
            {
                String message = "Employee with lastName: " + lastName + " not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }
            else return new ResponseEntity<>(smsService.getByLastName(lastName), HttpStatus.OK);
        }
        else {
            if (smsService.getByFirstNameAndLastName(firstName,lastName) == null) {
                String message = "Employee with firstName"+ firstName+ " and lastName: " + lastName + " not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            } else
                return new ResponseEntity<>(smsService.getByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);

        }
    }
    @PutMapping("/update/{Id}")
    public ResponseEntity<?> updateEmployee(@RequestBody SmsEmployee smsEmployee,
                                            @PathVariable("Id") int Id) {
        SmsEmployee existingSmsEmployee = smsService.getById(Id);
        if (existingSmsEmployee != null) {
            existingSmsEmployee.setFirstName(smsEmployee.getFirstName());
            existingSmsEmployee.setLastName(smsEmployee.getLastName());
           // existingSmsEmployee.setDesignation(smsEmployee.getDesignation());
            existingSmsEmployee.setDepartment(smsEmployee.getDepartment());
           // existingSmsEmployee.setDateOfBirth(smsEmployee.getDateOfBirth());
           // existingSmsEmployee.setPhoneNumber(smsEmployee.getPhoneNumber());
            existingSmsEmployee.setEmail(smsEmployee.getEmail());
            smsService.updateEmployee(existingSmsEmployee);
            String message = "Employee with ID: " + Id + " is updated";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }


        String message = "Employee with ID: " + Id + " not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("Id") int Id) {
        SmsEmployee smsEmployee = smsService.getById(Id);
        if (smsEmployee != null) {
            smsService.deleteEmployee(Id);
            String message = "Employee with ID: " + Id + " is deleted";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        String message = "Employee with ID: " + Id + " not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

//    @PostMapping("/logIn")
//    public ResponseEntity<CurrentUserSession> logIn(@RequestBody LogIn logIn) throws CurrentUserException {
//        CurrentUserSession session = smsService.logIn(logIn);
//        return new ResponseEntity<CurrentUserSession>(session,HttpStatus.OK);
//    }
@PostMapping("/logIn")
public ResponseEntity<?> logIn(@RequestBody LogIn logIn) {
    try {
        CurrentUserSession session = smsService.logIn(logIn);
        return new ResponseEntity<>(session, HttpStatus.OK);
    } catch (CurrentUserException e) {
        String errorMessage = e.getMessage();
       // HttpHeaders headers = new HttpHeaders();
      //  headers.add("Authorization", errorMessage); // Set the error message as Authorization header
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
}
    @DeleteMapping("/logOut/{userName}")
    public ResponseEntity<?> logOut(@PathVariable("userName") String userName )throws CurrentUserException {
        try {
            String message = smsService.logOut(userName);
            return new ResponseEntity<String>(message,HttpStatus.OK);
        }
        catch (CurrentUserException e) {
            String errorMessage = e.getMessage();
            // HttpHeaders headers = new HttpHeaders();
            //  headers.add("Authorization", errorMessage); // Set the error message as Authorization header
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }


    }

}
