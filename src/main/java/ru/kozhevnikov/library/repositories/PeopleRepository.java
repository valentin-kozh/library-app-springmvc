package ru.kozhevnikov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kozhevnikov.library.models.Person;
@Repository
public interface PeopleRepository  extends JpaRepository<Person, Integer> {
}
