package org.company.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("PMD.MethodArgumentCouldBeFinal")
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
