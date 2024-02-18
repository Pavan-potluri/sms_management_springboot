package com.pod5.sms_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CurrentUserSession {

    @Id
    private String userName;
    private int id;
    private String uuId;

}
