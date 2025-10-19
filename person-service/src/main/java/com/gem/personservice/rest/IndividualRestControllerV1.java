package com.gem.personservice.rest;

import com.gem.person.api.PersonApi;
import com.gem.person.dto.IndividualDto;
import com.gem.person.dto.IndividualPageDto;
import com.gem.person.dto.IndividualWriteDto;
import com.gem.person.dto.IndividualWriteResponseDto;
import com.gem.personservice.service.IndividualService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class IndividualRestControllerV1 implements PersonApi {

    private final IndividualService individualService;

    @Override
    public ResponseEntity<Void> compensateRegistration(UUID id) {
        individualService.hardDelete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        individualService.softDelete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<IndividualPageDto> findAllByEmail(List<@Email String> email) {
        var individuals = individualService.findByEmails(email);
        return ResponseEntity.ok().body(individuals);
    }

    @Override
    public ResponseEntity<IndividualDto> findById(UUID id) {
        var individual = individualService.findById(id);
        return ResponseEntity.ok().body(individual);
    }

    @Override
    public ResponseEntity<IndividualWriteResponseDto> registration(IndividualWriteDto individualWriteDto) {
        var individual = individualService.register(individualWriteDto);
        return ResponseEntity.ok().body(individual);
    }

    @Override
    public ResponseEntity<IndividualWriteResponseDto> update(UUID id, IndividualWriteDto individualWriteDto) {
        var individual = individualService.update(id, individualWriteDto);
        return ResponseEntity.ok().body(individual);
    }
}
