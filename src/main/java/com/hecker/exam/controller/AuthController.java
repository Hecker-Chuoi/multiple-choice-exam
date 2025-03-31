package com.hecker.exam.controller;

import com.hecker.exam.dto.request.auth.AuthenticationRequest;
import com.hecker.exam.dto.request.auth.IntrospectRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.AuthenticationResponse;
import com.hecker.exam.dto.response.IntrospectResponse;
import com.hecker.exam.service.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService service;

    @Operation(summary = "Get token", description = "Get token by username and password")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = {
                    @ExampleObject(value = "{\"username\":\"admin\",\"password\":\"admin\"}", name = "admin"),
                    @ExampleObject(value = "{\"username\":\"user\",\"password\":\"user\"}", name = "user")
            }
    ))
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Token received")
    })
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> getToken(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(service.getToken(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(service.introspect(request))
                .build();
    }
}
