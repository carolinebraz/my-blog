package com.generation.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.myblog.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long> {

}
