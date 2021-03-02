package com.gsg.demo.repository;

import com.gsg.demo.authentication.DefaultUserDetails;
import com.gsg.demo.authorization.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
                        UserRole.BOOK_READER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new DefaultUserDetails(
                        "user_book_writer",
                        passwordEncoder.encode("password2"),
                        UserRole.BOOK_WRITER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new DefaultUserDetails(
                        "user_book_eraser",
                        passwordEncoder.encode("password3"),
                        UserRole.BOOK_ERASER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ));
    }

}