package com.example.bookshop.service;

import com.example.bookshop.dto.token.AuthenticationRequest;
import com.example.bookshop.dto.token.IntrospectRequest;
import com.example.bookshop.dto.token.LogoutRequest;
import com.example.bookshop.dto.token.RefreshRequest;
import com.example.bookshop.dto.token.AuthenticationResponse;
import com.example.bookshop.dto.token.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
    void logout(LogoutRequest logout) throws ParseException, JOSEException;
     AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException;
}
