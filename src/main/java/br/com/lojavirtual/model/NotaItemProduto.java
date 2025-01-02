package br.com.lojavirtual.model;

import java.io.Serializable;

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

@Entity
@Table(name = "nota_item_produto")
@SequenceGenerator(name = "seq_nota_item_produto", sequenceName = "seq_nota_item_produto", initialValue = 1, allocationSize = 1)
public class NotaItemProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_nota_item_produto")
	private Long id;
	
	@Column(nullable = false)
	private Double qtd;

	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false,
				foreignKey = @ForeignKey(name = "produto_fk", value = ConstraintMode.CONSTRAINT))
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "nota_fiscal_compra_id", nullable = false,
				foreignKey = @ForeignKey(name = "nota_fiscal_compra_fk", value = ConstraintMode.CONSTRAINT))
	private NotaFiscalCompra notaFiscalCompra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQtd() {
		return qtd;
	}

	public void setQtd(Double qtd) {
		this.qtd = qtd;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public NotaFiscalCompra getNotaFiscalCompra() {
		return notaFiscalCompra;
	}

	public void setNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
		this.notaFiscalCompra = notaFiscalCompra;
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
		NotaItemProduto other = (NotaItemProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
