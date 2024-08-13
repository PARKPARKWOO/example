package com.example.hecto.controller;

import com.example.hecto.model.DateModel;
import com.example.hecto.service.DateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DateRequestController {
    private final DateRequestService dateRequestService;

    @GetMapping("/dates")
    public Flux<DateModel> getDates(@RequestParam(defaultValue = "1") int capacity) {
        return dateRequestService.requestDate(capacity);
    }
}
