package br.com.everis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.everis.model.Aluno;

@Repository
public interface Alunos extends JpaRepository<Aluno, Long>{

}
