package com.jhprog.dabank.login;

public interface IAuthentication {
    boolean handleAuthentication(String username, String password);
}
