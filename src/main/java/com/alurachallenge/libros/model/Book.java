package com.alurachallenge.libros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "language")
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "download_count")
    private Double downloadCount;

    // Constructors
    public Book() {}

    public Book(BookData bookData, Author author) {
        this.title = bookData.title();
        this.language = bookData.languages().isEmpty() ? "Unknown" : bookData.languages().get(0).getLanguageCode(); // Ajuste para idioma
        this.downloadCount = Double.valueOf(bookData.downloadCount());
        this.author = author;
    }
    public Book(String title, String language, Author author, Double downloadCount) {
        this.title = title;
        this.language = language;
        this.author = author;
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        StringBuilder bookData = new StringBuilder();
        bookData.append("*********** Book ***********\n");
        bookData.append("Title: ").append(title).append("\n");
        //bookData.append("Author: ").append(author != null ? author.getName() : "Unknown").append("\n");
        bookData.append("Language: ").append(language).append("\n");
        bookData.append("Download Count: ").append(downloadCount).append("\n");
        bookData.append("****************************\n");
        return bookData.toString();
    }

    // methods Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Double downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
