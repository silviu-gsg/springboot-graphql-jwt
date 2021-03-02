package com.gsg.demo.service;

import com.gsg.demo.entity.Book;
import com.gsg.demo.repository.BookRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@GraphQLApi
@Service
public class BookService {

    private final BookRepository bookRepository;

    @PreAuthorize("hasAnyRole('BOOK_READER', 'BOOK_WRITER', 'BOOK_ERASER')")
    @GraphQLQuery(name = "books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('BOOK_READER', 'BOOK_WRITER', 'BOOK_ERASER')")
    @GraphQLQuery(name = "book")
    public Optional<Book> getBookById(@GraphQLArgument(name = "id") Long id) {
        return bookRepository.findById(id);
    }

    @PreAuthorize("hasAuthority('book:write')")
    @GraphQLMutation(name = "saveBook")
    public Book saveBook(@GraphQLArgument(name = "book") Book book) {
        return bookRepository.save(book);
    }

    @PreAuthorize("hasAuthority('book:delete')")
    @GraphQLMutation(name = "deleteBook")
    public void deleteBook(@GraphQLArgument(name = "id") Long id) {
        bookRepository.deleteById(id);
    }

}