package com.PohonTautan.Repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PohonTautan.Entity.Log;

public interface LogRepository extends JpaRepository<Log, Integer>{
    
    @Query(value = "SELECT count(u) FROM log u WHERE DATE(created_at) = ?1 and id_user = ?2",
        countQuery = "SELECT count(*) FROM log WHERE DATE(created_at) = ?1 and id_user = ?2",
            nativeQuery = true)
    Long countbutton(Date ids, Integer idu);
}
