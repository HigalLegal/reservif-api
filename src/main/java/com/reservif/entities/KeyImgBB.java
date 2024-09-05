package com.reservif.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RegisterForReflection
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyImgBB {

    @Id
    private String value;
}