package com.java.springboot.service;

import com.java.springboot.entity.Token;

public interface TokenService {
    int addToken(Token token);
    int updateToken(Token token);
    Token findByUserId(int userid);

}
