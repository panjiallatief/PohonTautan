package com.PohonTautan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PohonTautan.Entity.Styles;

@Repository
public interface StylesRepository extends JpaRepository<Styles, Integer>{
    
}
