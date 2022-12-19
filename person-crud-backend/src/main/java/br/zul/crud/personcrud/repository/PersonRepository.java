package br.zul.crud.personcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.zul.crud.personcrud.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
