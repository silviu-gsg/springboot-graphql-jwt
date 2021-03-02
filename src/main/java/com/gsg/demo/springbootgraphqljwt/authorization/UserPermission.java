package com.gsg.demo.springbootgraphqljwt.authorization;

public enum UserPermission {
    BOOK_READ("book:read"),
    BOOK_WRITE("book:write"),
    BOOK_DELETE("book:delete");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
