package ru.kozhevnikov.library.modelDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.library.models.Book;
import ru.kozhevnikov.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class PersonDAO {
    private final SessionFactory sessionFactory;
    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery(
                "SELECT p FROM Person p", Person.class
        ).getResultList();
        return people;
    }
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }
    public Optional<Person> show(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p where p.name=:name", Person.class)
                .setParameter("name", name).stream().findAny();
    }
    @Transactional
    public void save(Person person){
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class,id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setBooks(updatedPerson.getBooks());
        personToBeUpdated.setYear(updatedPerson.getYear());
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class,id));
    }
}
