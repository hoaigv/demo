package com.example.bookshop.service.impl;

import com.example.bookshop.dto.request.AuthenticationRequest;
import com.example.bookshop.dto.request.IntrospectRequest;
import com.example.bookshop.dto.request.LogoutRequest;
import com.example.bookshop.dto.response.AuthenticationResponse;
import com.example.bookshop.dto.response.IntrospectResponse;
import com.example.bookshop.entity.InvalidatedToken;
import com.example.bookshop.entity.UserEntity;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import com.example.bookshop.exception.TokenAlreadyInvalidatedException;
import com.example.bookshop.repository.InvalidatedTokenRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.IAuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService implements IAuthenticationService {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw  new CustomRunTimeException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        var token = generateToken(user);


        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }



    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        boolean isValid = true;
        try {
             verifyToken(token);
        } catch (CustomRunTimeException e) {
            isValid = false;

        }
        return  IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public void logout(LogoutRequest logout) throws ParseException,JOSEException {
    try {
        var token = verifyToken(logout.getToken());


        String jit = token.getJWTClaimsSet().getJWTID();
        Date expiration = token.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryDate(expiration)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }catch (CustomRunTimeException e){
        log.error(e.getMessage());
    }

    }
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if(!(verified && expiration.after(new Date())))
            throw new CustomRunTimeException(ErrorCode.UNAUTHENTICATED);


         if(  invalidatedTokenRepository
                 .existsById(signedJWT.getJWTClaimsSet().getJWTID())){
             throw  new TokenAlreadyInvalidatedException(ErrorCode.UNAUTHENTICATED);
         }




        return signedJWT;
    }

    private  String generateToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("bookshop.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e) {
            log.info("can not generate token");
            throw new RuntimeException(e);
        }
    }
   private String buildScope(UserEntity user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role->{
                scopeJoiner.add("ROLE_"+role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach( permission ->
                        scopeJoiner.add(permission.getName())
                    );
                }
            });

        }
        return scopeJoiner.toString();
   }
}
