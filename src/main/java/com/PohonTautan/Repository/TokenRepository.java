package com.PohonTautan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PohonTautan.Entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
    
    @Query(value = "SELECT * FROM token where token = ?1",
            countQuery = "SELECT count(*) FROM token where token = ?1",
            nativeQuery = true)
    Token getiduser(String token);
}
