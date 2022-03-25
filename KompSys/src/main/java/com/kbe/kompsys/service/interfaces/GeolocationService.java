package com.kbe.kompsys.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dergil.kompsys.dto.geolocation.GeolocationResponse;

import java.net.UnknownHostException;

public interface GeolocationService {
    GeolocationResponse getGeolocation(String ipAddr) throws JsonProcessingException, UnknownHostException;
}
