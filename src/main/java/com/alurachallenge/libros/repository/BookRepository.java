package com.alurachallenge.libros.repository;
import com.alurachallenge.libros.model.Book;
import com.alurachallenge.libros.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);
    List<Book> findByLanguage(String language);

    @Query("SELECT b.author FROM Book b WHERE b.author.name LIKE %:name%")
    List<Author> findAuthorByName(@Param("name") String name);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    Optional<Book> findBookByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.language = :language")
    List<Book> findBooksByLanguage(@Param("language") String language);
}
