package com.reservif.entities;

import com.reservif.entities.enuns.TypeUser;
import com.reservif.entities.utils.TypeUserConverter;
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
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "identification_code", unique = true)
    private String identificationCode;

    @Column(nullable = false)
    @Convert(converter = TypeUserConverter.class)
    private TypeUser typeUser;

    @Embedded
    private ImageUser imageUser;

    // -----------------------------------------------------------------------------------

    public boolean checkForImage() {
        return imageUser != null;
    }

}
