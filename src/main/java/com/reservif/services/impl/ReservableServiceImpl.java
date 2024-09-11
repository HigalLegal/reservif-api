package com.reservif.services.impl;

import com.reservif.dto.mappers.ReservableMapper;
import com.reservif.dto.requests.ReservableRequest;
import com.reservif.dto.responses.ReservableResponse;
import com.reservif.entities.Reservable;
import com.reservif.repositories.ReservableRepository;
import com.reservif.services.ReservableService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ReservableServiceImpl implements ReservableService {

    @Inject
    private ReservableRepository reservableRepository;

    @Inject
    private ReservableMapper reservableMapper;

    // -----------------------------------------------------------------------------------------------------

    @Override
    public List<ReservableResponse> listAll(Integer page, Integer pageSize) {
        return reservableRepository
                .findAll(page, pageSize)
                .stream()
                .map(reservableMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservableResponse> listByName(String name) {
        return reservableRepository
                .findByName(name)
                .stream()
                .map(reservableMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReservableResponse byId(Integer id) {
        Reservable reservable = reservableRepository
                .findByIdOptional(id)
                .orElseThrow(() -> new EntityNotFoundException("Local inexistente"));

        return reservableMapper.entitieToResponse(reservable);
    }

    @Override
    public void create(ReservableRequest reservableRequest) {
        Reservable reservable = reservableMapper.requestToEntitie(reservableRequest);
        reservableRepository.persist(reservable);
    }

    @Override
    public void updateById(ReservableRequest reservableRequest, Integer id) {
        Reservable reservable = reservableMapper.requestToEntitie(reservableRequest);
        reservable.setId(id);

        reservableRepository.update(reservable);
    }

    @Override
    public void deleteById(Integer id) {
        boolean deleted = reservableRepository.deleteById(id);

        if(!deleted) {
            throw new EntityNotFoundException("Registro inexistente");
        }
    }
}
