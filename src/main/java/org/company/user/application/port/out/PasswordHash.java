package org.company.user.application.port.out;

public interface PasswordHash {

    String hash(String password);

    boolean checkPassword(String password, String hashedPassword);

}
