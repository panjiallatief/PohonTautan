package com.PohonTautan.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.PohonTautan.Entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{

    List<Users> findByUsername(String username);    
    
    List<Users> findByEmail(String email);


    @Query(value = "SELECT * FROM users where username = ?1",
            countQuery = "SELECT count(*) FROM users where username = ?1",
            nativeQuery = true)
    Users getidwithusername(String username);

    @Query(value = "SELECT * FROM users where email = ?1",
            countQuery = "SELECT count(*) FROM users where email = ?1",
            nativeQuery = true)
    Users getwithemail(String email);

    @Query(value = "SELECT (status) FROM users where username = ?1",
            countQuery = "SELECT count(*) FROM users where username = ?1",
            nativeQuery = true)
    boolean getstatus(String username);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM users c WHERE username = ?1",
        countQuery = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM users c WHERE username = ?1",
        nativeQuery = true)
    boolean booleanusercname(String username);

    
 
}
