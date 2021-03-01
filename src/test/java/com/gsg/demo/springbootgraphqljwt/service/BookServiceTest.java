package com.gsg.demo.springbootgraphqljwt.service;

import com.gsg.demo.springbootgraphqljwt.entity.Book;
import com.gsg.demo.springbootgraphqljwt.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookServiceTest {

    public static final String GRAPHQL_URL = "/graphql";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    @Order(0)
    void addOneBook() throws Exception {

        Book bookToSave = Book.builder()
                .isbn("123-456-789")
                .title("first title")
                .author("first author")
                .publisher("first publisher")
                .publishedAt(LocalDate.of(2020, 12, 20))
                .build();
        bookRepository.save(bookToSave);

        String booksQuery = "{\"query\":\"{ books { id isbn title author publisher publishedAt } }\"}";

        String expectedResponse = "{\"data\":{\"books\":[{\"id\":2,\"isbn\":\"123-456-789\",\"title\":\"first title\"," +
                "\"author\":\"first author\",\"publisher\":\"first publisher\",\"publishedAt\":\"2020-12-20\"}]}}";

        mockMvc.perform(post(GRAPHQL_URL)
                .content(booksQuery)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
                .andReturn();
    }

    @Test
    @Order(1)
    void addAndRemoveBook() throws Exception {
        String createBookMutation = "{\"query\":\"mutation { saveBook(book: { isbn: \\\"123-456-789\\\" title: " +
                "\\\"first title\\\" author: \\\"first author\\\" publisher: \\\"first publisher\\\" publishedAt: " +
                "\\\"2020-12-20\\\" }) { id isbn title author publisher publishedAt } }\"}";

        String newBookCreatedSuccessfulResponse = "{\"data\":{\"saveBook\":{\"id\":1,\"isbn\":\"123-456-789\"," +
                "\"title\":\"first title\",\"author\":\"first author\",\"publisher\":\"first publisher\"," +
                "\"publishedAt\":\"2020-12-20\"}}}";

        String allBooksQuery = "{\"query\":\"{ books { id isbn title } }\"}";

        String noBooksResponse = "{\"data\":{\"books\":[]}}";

        String deleteBookMutation = "{\"query\":\"mutation { deleteBook(id: 1) }\"}";

        // expect new book to not be there initially
        mockMvc.perform(post(GRAPHQL_URL)
                .content(allBooksQuery)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(noBooksResponse))
                .andReturn();

        // add new book successfully
        mockMvc.perform(post(GRAPHQL_URL)
                .content(createBookMutation)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(newBookCreatedSuccessfulResponse))
                .andReturn();

        // remove new book
        mockMvc.perform(post(GRAPHQL_URL)
                .content(deleteBookMutation)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // list book and expect new book to not be there
        mockMvc.perform(post(GRAPHQL_URL)
                .content(allBooksQuery)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(noBooksResponse))
                .andReturn();
    }

}
