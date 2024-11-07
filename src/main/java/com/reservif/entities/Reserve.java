package com.reservif.entities;

import com.reservif.entities.enuns.StatusReserve;
import com.reservif.entities.utils.StatusReserveConverter;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @Column(nullable = false)
    @Convert(converter = StatusReserveConverter.class)
    private StatusReserve status;

    @Column(nullable = false)
    private String observation;

    @Embedded
    private PeriodReserve period;

    @ManyToOne
    @JoinColumn(nullable = false, name = "reservable_id")
    private Reservable reservable;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public void updatePeriodReserve(PeriodReserve period) {
        this.period = period;
    }

}