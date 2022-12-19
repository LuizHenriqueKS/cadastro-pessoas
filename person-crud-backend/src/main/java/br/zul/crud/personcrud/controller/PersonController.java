package br.zul.crud.personcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.zul.crud.personcrud.model.response.PersonCreateResponseBody;
import br.zul.crud.personcrud.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<PersonCreateResponseBody> create(PersonCreateResponseBody requestBody) {
        PersonCreateResponseBody responseEntity = personService.create(requestBody);
        return ResponseEntity.ok(responseEntity);
    }

    @GetMapping
    public ResponseEntity<List<PersonCreateResponseBody>> findAll() {
        List<PersonCreateResponseBody> responseBody = personService.findAll();
        return ResponseEntity.ok(responseBody);
    }

}
