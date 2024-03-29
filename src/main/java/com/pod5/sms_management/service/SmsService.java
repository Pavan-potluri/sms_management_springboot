package com.pod5.sms_management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.pod5.sms_management.exception.CurrentUserException;
import com.pod5.sms_management.model.CurrentUserSession;
import com.pod5.sms_management.model.LogIn;
import com.pod5.sms_management.model.SmsEmployee;
import com.pod5.sms_management.repository.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pod5.sms_management.repository.SmsRepo;

@Service
public class  SmsService {

    @Autowired
    private SmsRepo smsRepo;


    @Autowired
    private SessionRepo sessionRepo;

    public void saveEmployee(SmsEmployee smsEmployee){

         if ("admin".equals(smsEmployee.getDepartment().toLowerCase())) {
            String userName = smsEmployee.getFirstName();
            String password = generatePassword(smsEmployee.getLastName(), smsEmployee.getFirstName());

            smsEmployee.setPassword(password);
            smsEmployee.setUserName(userName);
        }
        smsRepo.save(smsEmployee);
    }
    public void createEmployee(SmsEmployee smsEmployee) throws CurrentUserException{
        Optional<SmsEmployee> smsEmployee1 = smsRepo.findByEmail(smsEmployee.getEmail());
        if(smsEmployee1.isPresent()){
            throw new CurrentUserException("User details are already added");
        }else saveEmployee(smsEmployee);
    }
    public void updateEmployee(SmsEmployee smsEmployee){
        saveEmployee(smsEmployee);
    }

    public SmsEmployee getById(int id){
        SmsEmployee smsEmployee = smsRepo.findById(id).orElse(null);
        return smsEmployee;}

    public List<SmsEmployee> getAllEmployees() {
        return smsRepo.findAll();
    }


    private String generatePassword(String lastName, String firstName) {
        String suffix = lastName.substring(0, Math.min(lastName.length(), 3)).toLowerCase();
        String prefix = firstName.substring(0,Math.min(firstName.length(),3)).toLowerCase();
        return prefix + "@" + suffix;
    }


public void deleteEmployee(int id) {
    smsRepo.deleteById(id);
}

    public List<SmsEmployee> getByFirstName(String firstName) {
        List<SmsEmployee> smsEmployees = smsRepo.findByFirstName(firstName);
        return smsEmployees;
    }

    public List<SmsEmployee> getByLastName(String lastName) {
        List<SmsEmployee> smsEmployees = smsRepo.findByLastName(lastName);
        return smsEmployees;
    }

    public List<SmsEmployee> getByFirstNameAndLastName(String firstName, String lastName) {
        List<SmsEmployee> smsEmployees = smsRepo.findByFirstNameAndLastName(firstName, lastName);
        return smsEmployees;
    }

    public CurrentUserSession logIn(LogIn logIn) throws CurrentUserException{

        Optional<SmsEmployee> optionalSmsEmployee = smsRepo.findByUserName(logIn.getUserName());



        if (optionalSmsEmployee.isPresent() ){

            SmsEmployee smsEmployee = optionalSmsEmployee.get();

            if(smsEmployee.getPassword().equals(logIn.getPassword())){

                Optional<CurrentUserSession > optionalSession = sessionRepo.findById(logIn.getUserName());
                if(optionalSession.isEmpty()){

//                    String key = this.randomString();

                    CurrentUserSession session = new CurrentUserSession(logIn.getUserName(), smsEmployee.getId());

                    return sessionRepo.save(session);

                }else{
                    throw new CurrentUserException("User already logged In");
                }

            }else{
                throw new CurrentUserException("Password is Incorrect");
            }

        }else{
            throw new CurrentUserException("UserName is Incorrect");
        }

    }

    public String logOut(String userName) throws CurrentUserException{


        Optional<CurrentUserSession> optionalCurrentUserSession = sessionRepo.findByUserName(userName);

        if(optionalCurrentUserSession.isPresent()){
           CurrentUserSession session = optionalCurrentUserSession.get();
           sessionRepo.delete(session);
                    return "LogOut :"+session.getUserName();
        }else{
            throw new CurrentUserException("Wrong UserName or Already Logged Out");
        }
    }

//    private String randomString(){
//        String aplha = "abcedefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%&";
//        int length = 6;
//        StringBuilder str = new StringBuilder();
//
//        Random random = new Random();
//
//        for(int i=0; i<length;i++){
//            int index = random.nextInt(aplha.length());
//            str.append(aplha.charAt(index));
//
//        }
//        return str.toString();
//    }

}
