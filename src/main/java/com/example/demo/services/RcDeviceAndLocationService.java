package com.example.demo.services;


import com.maxmind.geoip2.exception.GeoIp2Exception;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface RcDeviceAndLocationService {
    void verifyDevice(String userName, HttpServletRequest request) throws IOException, GeoIp2Exception;

}
