/*
 * Author: Jani Olavi Heinikoski
 * Date: 15.04.2020
 * Version: alpha
 * Sources:
 * https://mvnrepository.com/artifact/commons-codec/commons-codec
 * https://en.wikipedia.org/wiki/Salt_(cryptography)
 * */
package com.jhprog.dabank.data;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Random;

public final class PasswordHandler {

    private static PasswordHandler passwordHandler = new PasswordHandler();
    private Random random;

    public static PasswordHandler getInstance() {
        return passwordHandler;
    }

    private PasswordHandler() {
        random = new SecureRandom();
    }

    public boolean passwordMatches(Password password, String passwordPlaintext) {
        String passwordAsString = passwordPlaintext + password.getSalt();
        String hashedPassword = new String(Hex.encodeHex(DigestUtils.sha256(passwordAsString)));
        return (hashedPassword.equals(password.getHash()));
    }

    public Password newPassword(String passwordPlaintext) {
        String salt = randomSalt();
        // hashedPassword is 64 characters long
        String hashedPassword = new String(Hex.encodeHex(DigestUtils.sha256((passwordPlaintext + salt))));
        return new Password(hashedPassword, salt);
    }

    private String randomSalt() {
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return new String(Hex.encodeHex(salt));
    }

}
