package ru.kozhevnikov.library.modelDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kozhevnikov.library.model.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query(
                "SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class)
        );
    }
    public Book show(int id){
        return jdbcTemplate.query(
                "SELECT * FROM Book WHERE book_id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)
        ).stream().findAny().orElse(null);
    }
    public Optional<Book> show(String name){
        return jdbcTemplate.query(
                "SELECT * FROM Book WHERE name=?",
                new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)
        ).stream().findAny();
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book (name, author, year) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }
    public void update(Book book, int id){

        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }
    public void take(Integer personId, int id){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
                personId, id);
    }
    public void release(int id){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
                null, id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

}
