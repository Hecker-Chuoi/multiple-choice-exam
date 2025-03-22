package com.hecker.exam.service;

import com.hecker.exam.dto.request.auth.AuthenticationRequest;
import com.hecker.exam.dto.request.auth.IntrospectRequest;
import com.hecker.exam.dto.response.AuthenticationResponse;
import com.hecker.exam.dto.response.IntrospectResponse;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.entity.User;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository repos;
    PasswordEncoder encoder;
    @NonFinal
    @Value(value = "${jwt.secret-key}")
    String secretKey;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

        SignedJWT jwt = SignedJWT.parse(request.getToken());
        JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();

        boolean isExpired = claimsSet.getExpirationTime().before(new Date());

        return IntrospectResponse.builder()
                .valid(jwt.verify(verifier) && !isExpired)
                .build();
    }

    public AuthenticationResponse getToken(AuthenticationRequest request) {
        User user = repos.findByUsername(request.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        if(!encoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(StatusCode.UNAUTHENTICATED);

        SecurityContext context = SecurityContextHolder.getContext();

        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(generateToken(user))
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("multiple-choice exam")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", user.getRole().name())
                .build();

        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
