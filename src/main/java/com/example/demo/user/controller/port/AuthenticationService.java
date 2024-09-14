package com.example.demo.user.controller.port;

import com.example.demo.user.domain.User;

public interface AuthenticationService {
    User login(long id);

    void verifyEmail(long id, String certificationCode);
}
