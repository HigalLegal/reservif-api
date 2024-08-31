package com.reservif.repositories;

import com.reservif.entities.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Integer> {

    public Optional<User> findByIdentificationCodeOrEmail(String identificationCodeOrEmail) {

        Parameters parameters = Parameters.with("value", identificationCodeOrEmail);

        return this
                .find("SELECT u FROM User u WHERE u.email = :value OR u.identificationCode = :value",
                    parameters)
                .firstResultOptional();

    }

}
