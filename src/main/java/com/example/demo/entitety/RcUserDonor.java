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
public class RcUserDonor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  private AppUser appUser;
  private LocalDateTime dateLastBloodGiving;
  @Builder.Default
  private Long numberOfBloodGiving= Long.valueOf(0);
  private LocalDateTime dateLastPlateletsGiving;
  @Builder.Default
  private Long numberOfPlateletsGiving= Long.valueOf(0);
  private LocalDateTime dateLastBloodPlasmaGiving;
  @Builder.Default
  private Long numberOfBloodPlasmaGiving= Long.valueOf(0);
  @Builder.Default
  private Boolean hasBeenRejected = false;
  @Builder.Default
  private Boolean sentNotification = false;
  @OneToOne
  private RcAddress address;
}
