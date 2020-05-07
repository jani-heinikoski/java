/*
 * Author: Jani Olavi Heinikoski
 * Date: 15.04.2020
 * Version: release
 * Sources:
 * -
 * */
package com.jhprog.dabank.data;

public final class Password {

    private String hash;
    private String salt;

    public Password(String hash, String salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

}
