package com.PohonTautan.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.PohonTautan.Entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{

    List<Users> findByUsername(String username);

    @Query(value = "SELECT * FROM users where username = ?1",
            countQuery = "SELECT count(*) FROM users where username = ?1",
            nativeQuery = true)
    Users getidwithusername(String username);

    
 
}
