package com.example.demo.entitety;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class DevicesAndLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RcDevice> devices;

}
