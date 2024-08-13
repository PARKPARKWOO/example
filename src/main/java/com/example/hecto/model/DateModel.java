package com.example.hecto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record DateModel(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
        LocalDate date,
        @JsonProperty("milliseconds_since_epoch")
        Long millisecondsSinceEpoch,
        String time
) {
}
