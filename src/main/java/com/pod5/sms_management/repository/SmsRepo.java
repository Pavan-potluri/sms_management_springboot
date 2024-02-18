package com.pod5.sms_management.repository;

import com.pod5.sms_management.model.SmsEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SmsRepo extends JpaRepository<SmsEmployee, Integer> {
    List<SmsEmployee> findByFirstName(String firstName);
    List<SmsEmployee> findByLastName(String lastName);

    List<SmsEmployee> findByFirstNameAndLastName(String firstName, String lastName);

    public Optional<SmsEmployee> findByUserName(String userName);


}
