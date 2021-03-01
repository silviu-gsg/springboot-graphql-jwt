package com.gsg.demo.springbootgraphqljwt.service;

import com.gsg.demo.springbootgraphqljwt.entity.Book;
import com.gsg.demo.springbootgraphqljwt.repository.BookRepository;
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

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @Test
    void persistBooks() throws Exception {

        Book bookToSave = Book.builder()
                .isbn("123-456-789")
                .title("first title")
                .author("first author")
                .publisher("first publisher")
                .publishedAt(LocalDate.of(2020, 12, 20))
                .build();
        bookRepository.save(bookToSave);

        String expectedResponse = "{\"data\":{\"books\":[{\"id\":1,\"isbn\":\"123-456-789\",\"title\":\"first title\"," +
                "\"author\":\"first author\",\"publisher\":\"first publisher\",\"publishedAt\":\"2020-12-20\"}]}}";

        mockMvc.perform(post("/graphql")
                .content("{\"query\":\"{ books { id isbn title author publisher publishedAt } }\"}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
                .andReturn();
    }

}
