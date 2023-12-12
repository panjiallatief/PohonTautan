package com.PohonTautan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;

import com.PohonTautan.Entity.Sessionid;

@Repository
public interface SessionoidRepositori extends JpaRepository<Sessionid, Integer>{

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE c.session_visitor = ?1",
        countQuery = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE c.session_visitor = ?1",
        nativeQuery = true)
    boolean findBySessionId(String ids);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE DATE(c.created_at) = ?1 and button is null",
        countQuery = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE DATE(c.created_at) = ?1 and button is null",
        nativeQuery = true)
    boolean findBycreatedAt(Date ids);
}
