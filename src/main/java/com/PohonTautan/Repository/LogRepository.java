package com.PohonTautan.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PohonTautan.Entity.Log;

public interface LogRepository extends JpaRepository<Log, Integer>{
    
}
