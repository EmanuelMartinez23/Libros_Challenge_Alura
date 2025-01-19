package com.alurachallenge.libros.repository;

import com.alurachallenge.libros.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.deathYear > :year OR a.deathYear IS NULL")
    List<Author> findAliveAuthors(@Param("year") Integer year);

    @Query("SELECT b FROM Book b WHERE b.language = :language")
    List<Book> findBooksByLanguage(@Param("language") String language);

    @Query("SELECT b FROM Book b")
    List<Book> findAllBooks();

    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int birthYear, int deathYear);

    List<Author> findByDeathYearIsNullOrDeathYearGreaterThanEqual(int deathYear);

    Author findByName(String name);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Author> findAuthorByName(@Param("name") String name);
}
