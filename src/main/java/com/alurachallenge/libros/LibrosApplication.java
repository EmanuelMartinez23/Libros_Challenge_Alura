package com.alurachallenge.libros;

import com.alurachallenge.libros.main.MainService;
import com.alurachallenge.libros.repository.AuthorRepository;
import com.alurachallenge.libros.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.alurachallenge.libros.repository")
public class LibrosApplication implements CommandLineRunner {

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;

	@Autowired
	private MainService mainService;

	public static void main(String[] args) {
		SpringApplication.run(LibrosApplication.class, args);
	}

	@Autowired
	public LibrosApplication(AuthorRepository authorRepository, BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		mainService.showMenu();
	}
}
