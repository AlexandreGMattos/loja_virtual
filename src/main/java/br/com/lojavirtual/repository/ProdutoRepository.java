package br.com.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import br.com.lojavirtual.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(nome) = ?1")
	public boolean existeProduto(String nome);
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(nome) = ?1 and empresa_id = ?2")
	public boolean existeProduto(String nome, Long idEmpresa);

	@Query("select c from Produto c where upper(trim(c.nome)) like %?1%")
	public List<Produto> buscarProdutoNome(String nome);
	
	@Query("select c from Produto c where upper(trim(c.nome)) like %?1% and c.empresa.id = ?2")
	public List<Produto> buscarProdutoNome(String nome, Long idEmpresa);

}
