package com.PohonTautan.Repository;

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
}
