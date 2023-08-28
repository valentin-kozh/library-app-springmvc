package ru.kozhevnikov.library.modelDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.library.models.Book;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class BookDAO {
    private final SessionFactory sessionFactory;
    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Book> index(){
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("Select b from Book b", Book.class).getResultList();
        return books;
    }
    public Book show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class,id);
    }
    public Optional<Book> show(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select b from Book b where name=:name")
                .setParameter("name", name).stream().findAny();
    }
    @Transactional
    public void save(Book book){
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        Session session = sessionFactory.getCurrentSession();
        Book bookToBeUpdated = session.get(Book.class,id);
        bookToBeUpdated.setOwner(updatedBook.getOwner());
        bookToBeUpdated.setName(updatedBook.getName());
        bookToBeUpdated.setYear(updatedBook.getYear());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class,id));
    }
//    public void take(Integer personId, int id){
//        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
//                personId, id);
//    }
//    public void release(int id){
//        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
//                null, id);
//    }
}
