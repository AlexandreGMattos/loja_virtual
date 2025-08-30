package br.com.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", initialValue = 1, allocationSize = 1)
public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;
	
	@NotNull(message = "O tipo produto deve ser informado")
	@Column(nullable=false)
	private String tipoUnidade;
	
	@Size(min = 5, message="Nome do produto deve ter pelo menos 5 letras")
	@NotNull(message = "Nome do produto deve ser informado")
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private Boolean ativo = Boolean.TRUE;
	
	@NotNull(message = "Descricao do produto deve ser informado")
	@Column(columnDefinition = "text", length = 2000, nullable=false)
	private String descricao;

	@NotNull(message = "Peso do produto deve ser informado")
	@Column(nullable=false)
	private Double peso;
	
	@NotNull(message = "Altura do produto deve ser informado")
	@Column(nullable=false)
	private Double altura;
	
	@NotNull(message = "Largura do produto deve ser informado")
	@Column(nullable=false)
	private Double largura;
	
	@NotNull(message = "Profundidade do produto deve ser informado")
	@Column(nullable=false)
	private Double profundidade;
	
	@NotNull(message = "Valor de venda do produto deve ser informado")
	@Column(nullable=false)
	private BigDecimal vlrVenda = BigDecimal.ZERO;
	
	@Column(nullable=false)
	private Integer qtdEstoque = 0;
	
	private Integer qtdAlertaEstoque = 0;
	
	private String linkYoutube;
	
	private Boolean alertQtdEstoque = Boolean.FALSE;
	
	private Integer qtdClique = 0;

	@NotNull(message = "A empresa deve ser informado")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, 
				foreignKey = @ForeignKey(name = "empresa_fk", value = ConstraintMode.CONSTRAINT))
	private PessoaJuridica empresa;
	
	
	@NotNull(message = "A categoria de produto deve ser informado")
	@ManyToOne
	@JoinColumn(name = "categoria_produto_id", nullable = false,
				foreignKey = @ForeignKey(name = "categoria_produto_fk", value = ConstraintMode.CONSTRAINT))
	private CategoriaProduto categoriaProduto;
	
	@NotNull(message = "A marca do produto deve ser informado")
	@ManyToOne
	@JoinColumn(name = "marca_produto_id", nullable = false,
				foreignKey = @ForeignKey(name = "marca_produto_fk", value = ConstraintMode.CONSTRAINT))
	private MarcaProduto marcaProduto;
	
	@NotNull(message = "A nota_item do produto deve ser informado")
	@ManyToOne
	@JoinColumn(name = "nota_item_produto_id", nullable = false,
				foreignKey = @ForeignKey(name = "nota_item_produto_fk", value = ConstraintMode.CONSTRAINT))
	private NotaItemProduto notaItemProduto;
	
	
	public void setNotaItemProduto(NotaItemProduto notaItemProduto) {
		this.notaItemProduto = notaItemProduto;
	}
	
	public NotaItemProduto getNotaItemProduto() {
		return notaItemProduto;
	}
	
	public void setMarcaProduto(MarcaProduto marcaProduto) {
		this.marcaProduto = marcaProduto;
	}
	
	public MarcaProduto getMarcaProduto() {
		return marcaProduto;
	}
	
	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}
	
	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}
	
	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}
	
	public PessoaJuridica getEmpresa() {
		return empresa;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getVlrVenda() {
		return vlrVenda;
	}

	public void setVlrVenda(BigDecimal vlrVenda) {
		this.vlrVenda = vlrVenda;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Integer getQtdAlertaEstoque() {
		return qtdAlertaEstoque;
	}

	public void setQtdAlertaEstoque(Integer qtdAlertaEstoque) {
		this.qtdAlertaEstoque = qtdAlertaEstoque;
	}

	public String getLinkYoutube() {
		return linkYoutube;
	}

	public void setLinkYoutube(String linkYoutube) {
		this.linkYoutube = linkYoutube;
	}

	public Boolean getAlertQtdEstoque() {
		return alertQtdEstoque;
	}

	public void setAlertQtdEstoque(Boolean alertQtdEstoque) {
		this.alertQtdEstoque = alertQtdEstoque;
	}

	public Integer getQtdClique() {
		return qtdClique;
	}

	public void setQtdClique(Integer qtdClique) {
		this.qtdClique = qtdClique;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
