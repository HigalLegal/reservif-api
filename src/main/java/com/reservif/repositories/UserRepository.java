package com.reservif.repositories;

import com.reservif.entities.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static com.reservif.repositories.utils.PaginationUtils.idealLimitReturn;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Integer> {

    @Inject
    private EntityManager entityManager;

    // ---------------------------------------------------------------------------------

    public List<User> findAll(int offset, int limit) {

        limit = idealLimitReturn(limit, offset, (int) this.count());

        return entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(Math.max(offset, 0))
                .setMaxResults(Math.max(limit, 0))
                .getResultList();
    }

    public Optional<User> findByIdentificationCodeOrEmail(String identificationCodeOrEmail) {

        Parameters parameters = Parameters.with("value", identificationCodeOrEmail);

        return this
                .find("SELECT u FROM User u WHERE u.email = :value OR u.identificationCode = :value",
                    parameters)
                .firstResultOptional();

    }

    public void update(User user) {
        final String JPQL = generateJPQL(user);
        Parameters parameters = generateParams(user);

        int affectedRows = this.update(JPQL, parameters);

        if(affectedRows <= 0) {
            throw new EntityNotFoundException("Registro de usuário inválido");
        }
    }

    private String generateJPQL(User user) {
        StringBuilder jpql = new StringBuilder("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName," +
                " u.email = :email, u.password = :password, u.identificationCode = :identificationCode," +
                " u.typeUser = :typeUser");

        if(user.checkForImage()) {
            jpql.append(", u.imageUser.defaultImageUrl = :defaultImageUrl, " +
                    "u.imageUser.thumbImageUrl = :thumbImageUrl, u.imageUser.mediumImageUrl = :mediumImageUrl");
        }

        jpql.append(" WHERE u.id = :id");

        return jpql.toString();
    }

    private Parameters generateParams(User user) {
        Parameters params = Parameters.with("firstName", user.getFirstName())
                .and("lastName", user.getLastName())
                .and("email", user.getEmail())
                .and("password", user.getPassword())
                .and("identificationCode", user.getIdentificationCode())
                .and("typeUser", user.getTypeUser());

        if(user.checkForImage()) {
            params.and("defaultImageUrl", user.getImageUser().getDefaultImageUrl())
                    .and("thumbImageUrl", user.getImageUser().getThumbImageUrl())
                    .and("mediumImageUrl", user.getImageUser().getMediumImageUrl());
        }

        return params;
    }

}
