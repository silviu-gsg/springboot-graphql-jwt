package com.gsg.demo.springbootgraphqljwt.service;

import com.gsg.demo.springbootgraphqljwt.entity.Book;
import com.gsg.demo.springbootgraphqljwt.repository.BookRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@GraphQLApi
@Service
public class BookService {

    private final BookRepository bookRepository;

    @GraphQLQuery(name = "books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GraphQLQuery(name = "book")
    public Optional<Book> getBookById(@GraphQLArgument(name = "id") Long id) {
        return bookRepository.findById(id);
    }

    @GraphQLMutation(name = "saveBook")
    public Book saveBook(@GraphQLArgument(name = "book") Book book) {
        return bookRepository.save(book);
    }

    @GraphQLMutation(name = "deleteBook")
    public void deleteBook(@GraphQLArgument(name = "id") Long id) {
        bookRepository.deleteById(id);
    }

}
