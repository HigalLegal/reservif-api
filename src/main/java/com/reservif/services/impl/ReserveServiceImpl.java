package com.reservif.services.impl;

import com.reservif.dto.mappers.ReserveMapper;
import com.reservif.dto.requests.ReserveRequest;
import com.reservif.dto.responses.ReserveResponse;
import com.reservif.entities.Reserve;
import com.reservif.entities.enuns.StatusReserve;
import com.reservif.repositories.ReserveRepository;
import com.reservif.services.ReserveService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReserveServiceImpl implements ReserveService {

    @Inject
    private ReserveRepository reserveRepository;

    @Inject
    private ReserveMapper reserveMapper;

    // ------------------------------------------------------------------------------------------------

    @Override
    public List<ReserveResponse> listAll(Integer page, Integer pageSize) {
        return reserveRepository
                .findAll(page, pageSize)
                .stream()
                .map(reserveMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReserveResponse> listByDateInterval(LocalDate beginning, LocalDate end) {
        return reserveRepository
                .findByDateInterval(beginning, end)
                .stream()
                .map(reserveMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReserveResponse> listByHoraryInterval(LocalTime beginning, LocalTime end) {
        return reserveRepository
                .findByHoraryInterval(beginning, end)
                .stream()
                .map(reserveMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReserveResponse> listByReservableName(String reservableName) {
        return reserveRepository
                .findByReservableName(reservableName)
                .stream()
                .map(reserveMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReserveResponse> listByStatus(StatusReserve statusReserve) {
        return reserveRepository
                .findByStatus(statusReserve)
                .stream()
                .map(reserveMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReserveResponse> listByUser(Integer userID) {
        return reserveRepository
                .findByUserId(userID)
                .stream()
                .map(reserveMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReserveResponse> listByReservable(Integer reservableID) {
        return reserveRepository
                .findByReservableId(reservableID)
                .stream()
                .map(reserveMapper::entitieToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReserveResponse byId(Integer id) {
        Reserve reserve = reserveRepository
                .findByIdOptional(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva inexistente"));

        return reserveMapper.entitieToResponse(reserve);
    }

    @Override
    public void create(ReserveRequest reserveRequest) {
        Reserve reserve = reserveMapper.requestToEntitie(reserveRequest);

        reserveRepository.persist(reserve);
    }

    @Override
    public void updateById(ReserveRequest reserveRequest, Integer id) {
        Reserve reserve = reserveMapper.requestToEntitie(reserveRequest);
        reserve.setId(id);

        reserveRepository.update(reserve);
    }

    @Override
    public void approve(Boolean approved, Integer id) {
        reserveRepository.approve(approved, id);
    }

    @Override
    public void deleteById(Integer id) {
        boolean deleted = reserveRepository.deleteById(id);

        if(!deleted) {
            throw new EntityNotFoundException("Registro inexistente");
        }
    }
}
