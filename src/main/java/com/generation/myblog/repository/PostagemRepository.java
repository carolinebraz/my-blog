package com.generation.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.myblog.model.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

}