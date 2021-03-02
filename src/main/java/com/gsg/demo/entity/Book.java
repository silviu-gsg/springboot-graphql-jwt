package com.gsg.demo.entity;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue
    @GraphQLQuery(name = "id", description = "A book's id")
    private Long id;

    @GraphQLQuery(name = "isbn", description = "A book's ISBN")
    private String isbn;

    @GraphQLQuery(name = "title", description = "A book's title")
    private String title;

    @GraphQLQuery(name = "author", description = "A book's author")
    private String author;

    private String publisher;

    private LocalDate publishedAt;
}
