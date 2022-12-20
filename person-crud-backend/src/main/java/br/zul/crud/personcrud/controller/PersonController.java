package br.zul.crud.personcrud.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.zul.crud.personcrud.dto.PersonDto;
import br.zul.crud.personcrud.model.request.PersonCreateRequestBody;
import br.zul.crud.personcrud.service.PersonService;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody @Valid PersonCreateRequestBody requestBody) {
        PersonDto responseEntity = personService.create(requestBody);
        return ResponseEntity.ok(responseEntity);
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> findAll() {
        List<PersonDto> responseBody = personService.findAll();
        return ResponseEntity.ok(responseBody);
    }

}
