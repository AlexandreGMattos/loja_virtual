package br.com.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.model.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

}
