package com.example.demo.entitety;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class RcDevice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String deviceName;
  private String deviceDetails;
  private String location;
  private LocalDateTime lastLogin;
  @Builder.Default
  private Boolean enabled = false;
}
