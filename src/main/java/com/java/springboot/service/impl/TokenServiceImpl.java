package com.java.springboot.service.impl;

import com.java.springboot.entity.Token;
import com.java.springboot.mapper.TokenMapper;
import com.java.springboot.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private TokenMapper tokenMapper;
    @Autowired
    public void setTokenMapper(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }
    public int addToken(Token token){
        return tokenMapper.addToken(token);
    }
    public int updateToken(Token token){
        return tokenMapper.updateToken(token);
    }
    public Token findByUserId(int userid){
        return tokenMapper.findByUserId(userid);
    }

}
