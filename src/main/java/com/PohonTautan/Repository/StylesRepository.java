package com.PohonTautan.Repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.PohonTautan.Entity.Styles;

@Repository
public interface StylesRepository extends JpaRepository<Styles, Integer>{
    
    @Query(value = "SELECT * FROM style where custom_url = ?1",
            countQuery = "SELECT count(*) FROM style where custom_url = ?1",
            nativeQuery = true)
    Styles getstStyles(String barcode);

    @Query(value = "SELECT * FROM style where id_user = ?1",
            countQuery = "SELECT count(*) FROM style where id_user = ?1",
            nativeQuery = true)
    Styles getstStyles2(Integer id_user);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM style c WHERE id_user = ?1",
        countQuery = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM style c WHERE id_user = ?1",
        nativeQuery = true)
    boolean booleanstyle(Integer id_user);
}
