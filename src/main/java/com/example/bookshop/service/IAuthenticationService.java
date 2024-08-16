package com.example.bookshop.service;

import com.example.bookshop.dto.request.AuthenticationRequest;
import com.example.bookshop.dto.request.IntrospectRequest;
import com.example.bookshop.dto.request.LogoutRequest;
import com.example.bookshop.dto.response.AuthenticationResponse;
import com.example.bookshop.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.Objects;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    void logout(LogoutRequest logout) throws ParseException, JOSEException;
}
