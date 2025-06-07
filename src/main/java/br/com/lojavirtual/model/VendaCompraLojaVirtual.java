package br.com.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vd_cp_loja_virtual")
@SequenceGenerator(name="seq_vd_cp_loja_virtual", sequenceName = "seq_vd_cp_loja_virtual", initialValue = 1, allocationSize = 1)
public class VendaCompraLojaVirtual implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_vd_cp_loja_virtual")
	private Long id;

	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_id", nullable = false,
				foreignKey = @ForeignKey(name = "pessoa_fk", value = ConstraintMode.CONSTRAINT))
	private Pessoa pessoa;
	
	
	@ManyToOne
	@JoinColumn(name = "endereco_entrega_id", nullable = false,
				foreignKey = @ForeignKey(name = "endereco_entrega_fk", value = ConstraintMode.CONSTRAINT))
	private Endereco enderecoEntrega;
	
	
	@ManyToOne
	@JoinColumn(name = "endereco_cobranca_id", nullable = false,
				foreignKey = @ForeignKey(name = "endereco_cobranca_fk", value = ConstraintMode.CONSTRAINT))
	private Endereco enderecoCobranca;
	
	@Column(nullable=false)
	private BigDecimal valorTotal;
	
	private BigDecimal valorDesconto;
	
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false,
				foreignKey = @ForeignKey(name = "forma_pagamento_fk", value = ConstraintMode.CONSTRAINT))
	private FormaPagamento formaPagamento;
	
	@OneToOne
	@JoinColumn(name="nota_fiscal_venda_id", nullable = false,
			foreignKey=@ForeignKey(name="nota_fiscal_venda_fk", value=ConstraintMode.CONSTRAINT))
	private NotaFiscalVenda notaFiscalVenda;
	
	
	@ManyToOne
	@JoinColumn(name = "cupom_desconto_id",
				foreignKey = @ForeignKey(name = "cupom_desconto_fk", value = ConstraintMode.CONSTRAINT))
	private CupomDesconto cupomDesconto;
	
	@Column(nullable=false)
	private BigDecimal valorFrete; 
	
	@Column(nullable=false)
	private Integer diaEntrega;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataVenda;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, 
				foreignKey = @ForeignKey(name = "empresa_fk", value = ConstraintMode.CONSTRAINT))
	private Pessoa empresa;
	
	
	public void setEmpresa(Pessoa empresa) {
		this.empresa = empresa;
	}
	
	public Pessoa getEmpresa() {
		return empresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Endereco getEnderecoCobranca() {
		return enderecoCobranca;
	}

	public void setEnderecoCobranca(Endereco enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public NotaFiscalVenda getNotaFiscalVenda() {
		return notaFiscalVenda;
	}

	public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) {
		this.notaFiscalVenda = notaFiscalVenda;
	}

	public CupomDesconto getCupomDesconto() {
		return cupomDesconto;
	}

	public void setCupomDesconto(CupomDesconto cupomDesconto) {
		this.cupomDesconto = cupomDesconto;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public Integer getDiaEntrega() {
		return diaEntrega;
	}

	public void setDiaEntrega(Integer diaEntrega) {
		this.diaEntrega = diaEntrega;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
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
		VendaCompraLojaVirtual other = (VendaCompraLojaVirtual) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}









