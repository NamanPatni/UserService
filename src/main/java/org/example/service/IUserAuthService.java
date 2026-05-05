package org.example.service;

import org.antlr.v4.runtime.misc.Pair;
import org.example.models.User;

import org.springframework.stereotype.Service;

@Service
public interface IUserAuthService {

    User createUser(String email, String password, String userName, String phoneNumber);

    Pair<User, String> login(String email, String password);

}
