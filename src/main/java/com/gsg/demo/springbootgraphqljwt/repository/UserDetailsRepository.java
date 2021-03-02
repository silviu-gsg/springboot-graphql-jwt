package com.gsg.demo.springbootgraphqljwt.repository;

import com.gsg.demo.springbootgraphqljwt.authentication.DefaultUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gsg.demo.springbootgraphqljwt.authorization.UserRole.BOOK_ERASER;
import static com.gsg.demo.springbootgraphqljwt.authorization.UserRole.BOOK_READER;
import static com.gsg.demo.springbootgraphqljwt.authorization.UserRole.BOOK_WRITER;

@AllArgsConstructor
@Repository
public class UserDetailsRepository {

    private final PasswordEncoder passwordEncoder;

    public Optional<DefaultUserDetails> findUserByUsername(String username) {
        return findAllUsers()
                .stream()
                .filter(defaultUserDetails -> username.equals(defaultUserDetails.getUsername()))
                .findFirst();
    }

    private List<DefaultUserDetails> findAllUsers() {
        return Arrays.asList(
                new DefaultUserDetails(
                        "user_book_reader",
                        passwordEncoder.encode("password1"),
                        BOOK_READER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new DefaultUserDetails(
                        "user_book_writer",
                        passwordEncoder.encode("password2"),
                        BOOK_WRITER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new DefaultUserDetails(
                        "user_book_eraser",
                        passwordEncoder.encode("password3"),
                        BOOK_ERASER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ));
    }

}