package org.company.user.infrastructure.out.hash;

import lombok.RequiredArgsConstructor;
import org.company.user.application.port.out.PasswordHash;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHashAdapter implements PasswordHash {

    private final PasswordEncoder encoder;

    @Override
    public String hash(final String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean checkPassword(final String password, final String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }

}
