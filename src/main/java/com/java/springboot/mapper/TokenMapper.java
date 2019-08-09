package com.java.springboot.mapper;

import com.java.springboot.entity.Token;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TokenMapper {
    int addToken(Token token);
    int updateToken(Token token);
    Token findByUserId(int userid);

}
