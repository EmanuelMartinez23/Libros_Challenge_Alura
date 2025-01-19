package com.alurachallenge.libros.main;

import com.alurachallenge.libros.model.Author;
import com.alurachallenge.libros.model.Book;
import com.alurachallenge.libros.model.BookData;
import com.alurachallenge.libros.model.Data;
import com.alurachallenge.libros.repository.AuthorRepository;
import com.alurachallenge.libros.repository.BookRepository;
import com.alurachallenge.libros.service.ApiClient;
import com.alurachallenge.libros.service.JsonConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class MainService {
    private final Scanner scanner = new Scanner(System.in);
    private final ApiClient apiConsumer = new ApiClient();
    private final String BASE_URL = "https://gutendex.com/books/";
    private final JsonConverter jsonConverter = new JsonConverter();
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public MainService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            String menu = "+-------------------------------------+\n" +
                    "|         CHOOSE AN OPTION           |\n" +
                    "+-------------------------------------+\n" +
                    "| 1 - Search book by title           |\n" +
                    "| 2 - List all registered books      |\n" +
                    "| 3 - List all registered authors    |\n" +
                    "| 4 - List authors alive in a year   |\n" +
                    "| 5 - List books by language         |\n" +
                    "| 0 - Exit                           |\n" +
                    "+-------------------------------------+";



            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> searchBookByTitle();
                case 2 -> listRegisteredBooks();
                case 3 -> listRegisteredAuthors();
                case 4 -> listLivingAuthors();
                case 5 -> handleLanguageMenu();
                case 0 -> System.out.println("Closing the application. Thank you for your queries.");
                default -> System.out.println("Invalid option");
            }
        }
    }

    private BookData getBookData() {
        System.out.println("Enter the book title: ");
        String bookTitle = scanner.nextLine();
        String jsonResponse = apiConsumer.fetchData(BASE_URL + "?search=" + bookTitle.replace(" ", "+"));
        Data data = jsonConverter.convertJsonToObject(jsonResponse, Data.class);
        if (data.results() != null && !data.results().isEmpty()) {
            return data.results().get(0);
        }
        return null;
    }
    private Book saveBook(BookData bookData, Author author) {
        List<Book> existingBooks = bookRepository.findByTitle(bookData.title());
        if (!existingBooks.isEmpty()) {
            return existingBooks.get(0);
        }
        Book book = new Book(bookData, author);
        return bookRepository.save(book);
    }

    private void searchBookByTitle() {
        BookData bookData = getBookData();
        if (bookData != null) {
            Author author = bookData.authors().stream()
                    .map(datt -> {
                        Author existingAuthor = authorRepository.findByName(datt.name());
                        if (existingAuthor != null) {
                            return existingAuthor;
                        } else {
                            Author newAuthor = new Author(datt);
                            return authorRepository.save(newAuthor);
                        }
                    })
                    .findFirst()
                    .orElse(null);
            if (author != null) {
                Book book = saveBook(bookData, author);
                System.out.println(book);
            }
        } else {
            System.out.println("********************************************************");
            System.out.println("* No data found for the provided book title *");
            System.out.println("********************************************************");
        }
    }

    private void listRegisteredBooks() {
        System.out.println("The registered books are: ");
        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("******************************");
            System.out.println("* No books are registered. *");
            System.out.println("******************************");
        }
    }


    private void listRegisteredAuthors() {
        System.out.println("The registered authors are: ");
        List<Author> authors = authorRepository.findAll();
        if (!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("No authors are registered.");
        }
    }

    private void listLivingAuthors() {
        System.out.println("Enter the year to check which authors were alive: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer
        List<Author> authors = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
        if (!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("##############################################################");
            System.out.println("#                                                          #");
            System.out.println("#   NO LIVING AUTHORS FOUND FOR THE PROVIDED YEAR.        #");
            System.out.println("#                                                          #");
            System.out.println("##############################################################");

        }
    }

    private void showLanguageMenu() {
        System.out.println("***** CHOOSE A LANGUAGE *****");
        System.out.println("1. Spanish ");
        System.out.println("2. French ");
        System.out.println("3. English ");
        System.out.println("4. Portuguese ");
        System.out.println("0. Back to main menu");
        System.out.println("***************************");
        System.out.print("Choose an option: ");
    }

    public void handleLanguageMenu() {
        int languageOption = -1;
        while (languageOption != 0) {
            showLanguageMenu();
            languageOption = scanner.nextInt();
            scanner.nextLine();

            switch (languageOption) {
                case 1 -> listBooksByLanguage("es");
                case 2 -> listBooksByLanguage("fr");
                case 3 -> listBooksByLanguage("en");
                case 4 -> listBooksByLanguage("pt");
                case 0 -> System.out.println("Returning to the main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private String getLanguageName(String languageCode) {
        switch (languageCode) {
            case "es":
                return "Spanish";
            case "fr":
                return "French";
            case "en":
                return "English";
            case "pt":
                return "Portuguese";
            default:
                return "Unknown";
        }
    }

    private void listBooksByLanguage(String language) {
        String languageName = getLanguageName(language);
        List<Book> books = bookRepository.findBooksByLanguage(language);
        if (!books.isEmpty()) {
            System.out.println("The books in " + languageName + " are: ");
            books.forEach(System.out::println);
        } else {
            System.out.println("No books found in the " + languageName + " language.");
        }
    }
}
