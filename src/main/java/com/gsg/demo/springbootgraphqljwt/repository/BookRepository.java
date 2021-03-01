package com.gsg.demo.springbootgraphqljwt.repository;

import com.gsg.demo.springbootgraphqljwt.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
