package com.example.demo.Data.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Data.Constants.TokenConstants;
import com.example.demo.Data.Entities.Token;

@Repository
public interface TokenRepo  extends JpaRepository<Token,Integer>{
    
    @Query(name = TokenConstants.FIND_ALL_VALID_TOKENS_BY_USER)
    List<Token> findAllUserValidTokens(@Param("userId") Integer userId);

    Optional<Token> findByJwt(String jwt);
}
