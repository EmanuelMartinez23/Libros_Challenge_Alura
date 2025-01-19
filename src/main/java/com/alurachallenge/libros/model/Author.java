package com.alurachallenge.libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonAlias("birthDate")
    @Column(name = "birth_year")
    private Integer birthYear;

    @JsonAlias("deathDate")
    @Column(name = "death_year")
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    // Constructors
    public Author() {

    }

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birthYear = authorData.birthYear();
        this.deathYear = authorData.deathYear();
    }

    // toString for objects
    @Override
    public String toString() {
        StringBuilder dataAuthor = new StringBuilder();
        dataAuthor.append("*********** Author Data ***********\n");
        dataAuthor.append("Name: ").append(name).append("\n");
        dataAuthor.append("Birth Date: ").append(birthYear).append("\n");
        dataAuthor.append("Death Date: ").append(deathYear).append("\n");
        dataAuthor.append("****************************\n");
        return dataAuthor.toString();
    }

    // Methods Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
