package com.reservif.entities;

import com.reservif.entities.enuns.StatusReserve;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private PeriodReserve period;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusReserve status;

    @Column(nullable = false)
    private String observation;

    @ManyToOne
    @JoinColumn(nullable = false, name = "reservable_id")
    private Reservable reservable;

}