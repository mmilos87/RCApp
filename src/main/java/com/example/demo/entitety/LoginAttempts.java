package com.example.demo.entitety;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class LoginAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    private LocalDateTime dateOfLastAttempt;
    @Builder.Default
    private Integer numberOfFailureAttempts=0;

    public void increaseNumberOfFailureAttempts(){
        numberOfFailureAttempts++;
    }
    public void resetNumberOfFailureAttempts(){
        numberOfFailureAttempts=0;
        dateOfLastAttempt =LocalDateTime.now();
    }

}
