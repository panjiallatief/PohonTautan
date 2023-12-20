package com.PohonTautan.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PohonTautan.Entity.Log;

public interface LogRepository extends JpaRepository<Log, Integer>{
    
    @Query(value = "SELECT count(u) FROM log u WHERE DATE(created_at) = ?1 and id_user = ?2",
        countQuery = "SELECT count(*) FROM log WHERE DATE(created_at) = ?1 and id_user = ?2",
            nativeQuery = true)
    Long countbutton(Date ids, Integer idu);

    @Query(value = "SELECT DATE(u.created_at) as date, COUNT(u) AS total_count " +
            "FROM log u " +
            "WHERE EXTRACT(MONTH FROM u.created_at) = ?1 " +
            "AND EXTRACT(YEAR FROM u.created_at) = ?2 " +
            "AND u.id_user = ?3 " +
            "GROUP BY date", 
            nativeQuery = true)
    List<Object[]> countPerMonth(int month, int year, Integer userId);

    
}
