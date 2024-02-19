package com.pod5.sms_management.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SmsEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    //private String designation;
    private String department;
  //  private LocalDate dateOfBirth;
   // private long phoneNumber;
    private String email;
    private String userName;
    //@JsonIgnore
    private String password;




//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "cred_id", referencedColumnName = "id")
//    @JsonIgnore
//    private SmsEmployeeCredentials smsEmployeeCredentials;

}
