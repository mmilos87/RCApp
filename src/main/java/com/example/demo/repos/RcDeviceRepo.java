package com.example.demo.repos;

import com.example.demo.entitety.RcDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RcDeviceRepo extends JpaRepository<RcDevice,Long> {
}
