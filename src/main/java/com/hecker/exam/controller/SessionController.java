package com.hecker.exam.controller;

import com.hecker.exam.dto.request.SessionCreationRequest;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.service.SessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionController {
    SessionService service;

    @PostMapping
    public TestSession createSession(@RequestBody SessionCreationRequest request){
        return service.createSession(request);
    }

    @PutMapping("/{sessionId}")
    public TestSession updateSession(@PathVariable long sessionId, @RequestBody SessionCreationRequest request){
        return service.updateSession(sessionId, request);
    }

    @GetMapping("/{sessionId}")
    public TestSession getSession(@PathVariable long sessionId){
        return service.getSession(sessionId);
    }

    @PostMapping("/{sessionId}/candidates")
    public TestSession candidateAssign(@PathVariable long sessionId, @RequestBody List<Long> candidateIds){
        return service.candidateAssign(sessionId, candidateIds);
    }
}
