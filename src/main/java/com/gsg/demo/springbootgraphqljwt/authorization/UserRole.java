package com.gsg.demo.springbootgraphqljwt.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gsg.demo.springbootgraphqljwt.authorization.UserPermission.BOOK_DELETE;
import static com.gsg.demo.springbootgraphqljwt.authorization.UserPermission.BOOK_READ;
import static com.gsg.demo.springbootgraphqljwt.authorization.UserPermission.BOOK_WRITE;

@AllArgsConstructor
@Getter
public enum UserRole {
    BOOK_READER(new HashSet<>(Collections.singletonList(BOOK_READ))),
    BOOK_WRITER(new HashSet<>(Arrays.asList(BOOK_READ, BOOK_WRITE))),
    BOOK_ERASER(new HashSet<>(Arrays.asList(BOOK_READ, BOOK_WRITE, BOOK_DELETE)));

    private final Set<UserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> roleAndPermissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        roleAndPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return roleAndPermissions;
    }

}