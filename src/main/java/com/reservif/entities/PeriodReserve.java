package com.reservif.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PeriodReserve {

    @Column(nullable = false, name = "start_day")
    private LocalDate startDay;

    @Column(nullable = false, name = "end_day")
    private LocalDate endDay;

    @Column(nullable = false, name = "start_horary")
    private LocalTime startHorary;

    @Column(nullable = false, name = "end_horary")
    private LocalTime endHorary;

    @ElementCollection
    @Convert(converter = DayOfWeek.class)
    private List<DayOfWeek> daysOfWeek = new ArrayList<>();

}
