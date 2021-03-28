package com.example.demo.entitety;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
