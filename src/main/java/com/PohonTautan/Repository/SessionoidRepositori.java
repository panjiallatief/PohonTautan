package com.PohonTautan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

import com.PohonTautan.Entity.Sessionid;

@Repository
public interface SessionoidRepositori extends JpaRepository<Sessionid, Integer>{

    // @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE c.session_visitor = ?1",
    //     countQuery = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE c.session_visitor = ?1",
    //     nativeQuery = true)
    // boolean findBySessionId(String ids);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE DATE(c.created_at) = ?1 and c.session_visitor = ?2",
        countQuery = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM sessionid c WHERE DATE(c.created_at) = ?1 and c.session_visitor = ?2",
        nativeQuery = true)
    boolean findBycreatedAt(Date ids, String username);

    @Query(value = "SELECT count(u) FROM sessionid u WHERE DATE(created_at) = ?1 and id_user = ?2",
        countQuery = "SELECT count(*) FROM sessionid WHERE DATE(created_at) = ?1 and id_user = ?2",
            nativeQuery = true)
    Long countsession(Date ids, Integer idu);

    // @Query("SELECT u.created_at, count(u) FROM sessionid u " +
    //         "WHERE EXTRACT(MONTH FROM u.created_at) = ?1 " +
    //         "AND EXTRACT(YEAR FROM u.created_at) = ?2 " +
    //         "AND u.id_user = ?3 " +
    //         "GROUP BY u.created_at")
    // List<Sessionid> countpermount(int month, int year, Integer userId);


}
