package com.example.hecto.service;

import com.example.hecto.model.DateModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class DateRequestService {
    private final WebClient webClient;

    public Flux<DateModel> requestDate(int capacity) {
        return Flux.range(0, capacity)
                .flatMap(i -> webClient.get()
                        .uri("/")
                        .retrieve()
                        .bodyToMono(DateModel.class));
    }
}
