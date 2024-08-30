package com.reservif.dto.mappers.impl;

import com.reservif.dto.ImageUserDTO;
import com.reservif.dto.mappers.Mapper;
import com.reservif.dto.requests.UserRequest;
import com.reservif.dto.responses.UserResponse;
import com.reservif.entities.ImageUser;
import com.reservif.entities.User;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("User")
public class UserMapperImpl implements Mapper<User, UserRequest, UserResponse> {

    @Override
    public User requestToEntitie(UserRequest request) {
        return User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(generateEncryptedPassword(request.getPassword()))
                .identificationCode(request.getIdentificationCode())
                .typeUser(request.getTypeUser())
                .build();
    }

    @Override
    public UserResponse entitieToResponse(User entitie) {
        return UserResponse
                .builder()
                .id(entitie.getId())
                .firstName(entitie.getFirstName())
                .lastName(entitie.getLastName())
                .email(entitie.getEmail())
                .identificationCode(entitie.getIdentificationCode())
                .typeUser(entitie.getTypeUser())
                .imageUser(imageUserEntitieToDTO(entitie.getImageUser()))
                .build();

    }

    private ImageUserDTO imageUserEntitieToDTO(ImageUser imageUser) {
        return ImageUserDTO
                .builder()
                .defaultImageUrl(imageUser.getDefaultImageUrl())
                .mediumImageUrl(imageUser.getMediumImageUrl())
                .thumbImageUrl(imageUser.getThumbImageUrl())
                .build();
    }

    private String generateEncryptedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
