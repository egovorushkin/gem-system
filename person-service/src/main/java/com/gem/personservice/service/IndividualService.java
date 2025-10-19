package com.gem.personservice.service;


import com.gem.person.dto.IndividualDto;
import com.gem.person.dto.IndividualPageDto;
import com.gem.person.dto.IndividualWriteDto;
import com.gem.person.dto.IndividualWriteResponseDto;
import com.gem.personservice.exception.PersonException;
import com.gem.personservice.mapper.IndividualMapper;
import com.gem.personservice.repository.IndividualRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Setter
@Service
@RequiredArgsConstructor
public class IndividualService {

    private final IndividualMapper individualMapper;
    private final IndividualRepository individualRepository;

    @Transactional
    public IndividualWriteResponseDto register(IndividualWriteDto writeDto) {
        var individual = individualMapper.to(writeDto);
        individualRepository.save(individual);
        log.info("IN - register: individual: [{}] registered successfully", individual.getUser().getEmail());
        return new IndividualWriteResponseDto(individual.getId().toString());
    }

    public IndividualPageDto findByEmails(List<String> emails) {
        var individuals = individualRepository.findAllByEmails(emails);
        var from = individualMapper.from(individuals);
        var individualPageDto = new IndividualPageDto();
        individualPageDto.setItems(from);
        return individualPageDto;
    }

    public IndividualDto findById(UUID id) {
        var individual = individualRepository.findById(id)
                .orElseThrow(() -> new PersonException("Individual with id: " + id + " not found"));
        log.info("IN - findById: individual: [{}] found", individual);
        return individualMapper.from(individual);
    }

    @Transactional
    public void softDelete(UUID id) {
        log.info("IN - softDelete: individual: [{}] deleted successfully", id);
        individualRepository.softDelete(id);
    }

    @Transactional
    public void hardDelete(UUID id) {
        var individual = individualRepository.findById(id)
                .orElseThrow(() -> new PersonException("Individual with id: " + id + " not found"));
        log.info("IN - hardDelete: individual: [{}] deleted successfully", id);
        individualRepository.delete(individual);
    }

    @Transactional
    public IndividualWriteResponseDto update(UUID id, IndividualWriteDto writeDto) {
        var individual = individualRepository.findById(id)
                .orElseThrow(() -> new PersonException("Individual with id: " + id + " not found"));
        individualMapper.update(individual, writeDto);
        individualRepository.save(individual);
        log.info("IN - update: individual: [{}] updated successfully", id);
        return new IndividualWriteResponseDto(individual.getId().toString());
    }
}
