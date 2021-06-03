package com.example.demo.services;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.DevicesAndLocations;
import com.example.demo.entitety.RcDevice;
import com.example.demo.repos.AppUserRepository;
import com.example.demo.repos.DevicesAndLocationsRepo;
import com.example.demo.repos.RcDeviceRepo;
import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RcDeviceAndLocationServiceImpl implements RcDeviceAndLocationService {
  private final DevicesAndLocationsRepo devicesAndLocationsRepo;
  private final RcDeviceRepo rcDeviceRepo;
  private final AppUserRepository appUserRepository;
  private final DatabaseReader databaseReader;
  private final Parser parser;
  private static final String UNKNOWN = "UNKNOWN";

  @Override
  public void verifyDevice(String userName, HttpServletRequest request)
      throws IOException, GeoIp2Exception {
    String location = extractLocation(request);
    String deviceDetails = extractDeviceDetails(request);
    String deviceName = extractDeviceName(request);
    AppUser appUser = appUserRepository.findByEmail(userName).get();
    devicesAndLocationsRepo
        .findByAppUser(appUser)
        .ifPresentOrElse(
            devices -> {
              devices.getDevices().stream()
                  .filter(name -> name.getDeviceName().equals(deviceName))
                  .filter(details -> details.getDeviceDetails().equals(deviceDetails))
                  .filter(enabled -> enabled.getEnabled().equals(true))
                  .findFirst()
                  .ifPresentOrElse(
                      device -> { // prijavljivanje sa odobrenog uredjaja
                        device.setLocation(location);
                        device.setLastLogin(LocalDateTime.now());
                        rcDeviceRepo.save(device);
                      },
                      () -> {
                        // svako prijavljivanje sa novog uredjaja  posle prvog prijavljivanja
                        //   dva slucaja
                        //   prvi kad je uredja u bp ali nije odobren
                        //   drugi kad  uredjaj nije u bazi podataka
                        DevicesAndLocations userDevicesAndLocations =
                            devicesAndLocationsRepo.findByAppUser(appUser).get();
                        userDevicesAndLocations.getDevices().stream()
                            .filter(name -> name.getDeviceName().equals(deviceName))
                            .filter(detail -> detail.getDeviceDetails().equals(deviceDetails))
                            .findFirst()
                            .ifPresentOrElse(
                                device -> { // prvi
                                  throw new IllegalStateException(
                                      "device " + device.getDeviceDetails() + " are not enabled");
                                  // todo obraditi izuzetak
                                },
                                () -> { // drugi
                                  RcDevice device =
                                          RcDevice.builder()
                                          .deviceName(deviceName)
                                          .deviceDetails(deviceDetails)
                                          .location(location)
                                          .lastLogin(LocalDateTime.now())
                                          .enabled(false)
                                          .build();
                                  userDevicesAndLocations.getDevices().add(device);
                                  devicesAndLocationsRepo.save(userDevicesAndLocations);
                                  throw new IllegalAccessError("DEVICE ARE NOT IN LIST");
                                  // todo mail servis koji ce da salje mail  sa linkom za aktivaciju
                                  // todo obraditi izuzetak
                                  // uredjaja
                                });
                      });
            },
            () -> { // slucaj  kada se prvi purt prijavljuje na sistem
              RcDevice device =
                      RcDevice.builder()
                      .deviceName(deviceName)
                      .deviceDetails(deviceDetails)
                      .location(location)
                      .lastLogin(LocalDateTime.now())
                      .enabled(true)
                      .build();
              DevicesAndLocations userDevicesAndLocations =
                  DevicesAndLocations.builder()
                      .appUser(appUser)
                      .devices(Collections.singletonList(device))
                      .build();
              devicesAndLocationsRepo.save(userDevicesAndLocations);
            });
  }

  private String extractDeviceName(HttpServletRequest request) {
    Client client = parser.parse(request.getHeader("user-agent"));
    if (Objects.nonNull(client)) {
      return client.device.family;
    } else {
      return UNKNOWN;
    }
  }

  private String extractDeviceDetails(HttpServletRequest request) {
    Client client = parser.parse(request.getHeader("user-agent"));
    if (Objects.nonNull(client)) {
      return new StringBuilder()
          .append(client.os.family)
          .append(" ")
          .append(client.os.major)
          .append(".")
          .append(client.os.minor)
          .append(" - ")
          .append(client.userAgent.family)
          .append(" ")
          .append(client.userAgent.major)
          .append(".")
          .append(client.userAgent.minor)
          .toString();
    } else {
      return UNKNOWN;
    }
  }

  private String extractLocation(HttpServletRequest request) throws IOException, GeoIp2Exception {
    String clientIp;
    String clientXForwardedForIp = request.getHeader("x-forwarded-for");
    if (Objects.nonNull(clientXForwardedForIp)) {
      clientIp = clientXForwardedForIp.split(" *, *")[0];
    } else {
      clientIp = request.getRemoteAddr();
    }
    InetAddress ipAddress = InetAddress.getByName(clientIp);
    CityResponse cityResponse = databaseReader.city(ipAddress);
    if (Objects.nonNull(cityResponse)
        && Objects.nonNull(cityResponse.getCity())
        && !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {
      return cityResponse.getCity().getName();
    } else {
      return UNKNOWN;
    }
  }
}
