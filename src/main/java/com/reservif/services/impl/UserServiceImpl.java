package com.reservif.services.impl;

import com.reservif.clients.ApiImgBB;
import com.reservif.clients.model.Root;
import com.reservif.configs.jwt.TokenGenerator;
import com.reservif.dto.mappers.UserMapper;
import com.reservif.dto.requests.UserLoginRequest;
import com.reservif.dto.requests.UserRequest;
import com.reservif.dto.responses.TokenResponse;
import com.reservif.dto.responses.UserResponse;
import com.reservif.entities.ImageUser;
import com.reservif.entities.User;
import com.reservif.exceptions.PasswordException;
import com.reservif.repositories.KeyImgBbRepository;
import com.reservif.repositories.UserRepository;
import com.reservif.services.UserService;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private KeyImgBbRepository keyAPI;

    @Inject
    private UserMapper userMapper;

    @Inject
    private TokenGenerator tokenGenerator;

    @RestClient
    private ApiImgBB externalApiForImage;

    // ----------------------------------------------------------------------------

    @Override
    public List<UserResponse> listAll(
            @QueryParam("page") @Nullable Integer page,
            @QueryParam("pageSize") @Nullable Integer pageSize
    ) {
        return userRepository
                .findAll(page, pageSize)
                .stream()
                .map(user -> userMapper.entitieToResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse byId(Integer id) {
        return userMapper.entitieToResponse(userRepository.findById(id));
    }

    @Override
    public TokenResponse login(UserLoginRequest login) {

        User user = userRepository
                .findByIdentificationCodeOrEmail(login.getEmailOrCode())
                .orElseThrow(() -> new EntityNotFoundException("Email ou código SUAP inexistente."));

        if(!checkPassword(login.getPassword(), user.getPassword())) {
            throw new PasswordException("Senha incorreta");
        }

        String token = tokenGenerator.createToken(user);

        return new TokenResponse(token);
    }

    @Override
    public void create(UserRequest userRequest, File image) {
        User user = userMapper.requestToEntitie(userRequest);

        if(image != null) {
            ImageUser imageUser = uploadImage(keyAPI.returnKey(), image);
            user.setImageUser(imageUser);
        }

        userRepository.persistUser(user);
    }

    @Override
    public void update(UserRequest userRequest, File image, Integer id) {
        User user = userMapper.requestToEntitie(userRequest);
        user.setId(id);

        if(image != null) {
            ImageUser imageUser = uploadImage(keyAPI.returnKey(), image);
            user.setImageUser(imageUser);
        }

        userRepository.update(user);
    }

    @Override
    public void deleteById(Integer id) {
        boolean deleted = userRepository.deleteById(id);

        if(!deleted) {
            throw new EntityNotFoundException("Registro inexistente");
        }

    }

    private ImageUser uploadImage(String key, File image) {
        Root dataAPI = externalApiForImage.uploadImage(key, image);

        System.out.println(dataAPI.getData().getMedium());

        return ImageUser
                .builder()
                .defaultImageUrl(dataAPI.defaultUrlImage())
                .thumbImageUrl(dataAPI.thumbUrlImage())
                .build();
    }

    private boolean checkPassword(String password, String encriptedPassword) {
        return BCrypt.checkpw(password, encriptedPassword);
    }

}
