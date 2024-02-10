package domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "tb_ProductQuantity")
public class ProductQuantity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prod_qtd_seq")
	@SequenceGenerator(name="prod_qtd_seq", sequenceName="sq_prod_qtd", initialValue = 1, allocationSize = 1)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "valor_total", nullable = false)
	private Double totalValue;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_sale_fk", 
		foreignKey = @ForeignKey(name = "fk_prod_qtd_sale"), 
		referencedColumnName = "id", nullable = false
	)
	private Sale sale;
	
	public ProductQuantity(Long id, Product product, Integer quantity, Double totalValue, Sale sale) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.totalValue = totalValue;
		this.sale = sale;
	}
	
	public ProductQuantity(Product product, Integer quantity, Double totalValue, Sale sale) {
		this.product = product;
		this.quantity = quantity;
		this.totalValue = totalValue;
		this.sale = sale;
	}

	public ProductQuantity() {
		this.quantity = 0;
		this.totalValue = 0.0;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Product getproduct() {
		return product;
	}

	public void setproduct(Product product) {
		this.product = product;
	}

	public Integer getquantity() {
		return quantity;
	}

	public void setquantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double gettotalValue() {
		return totalValue;
	}

	public void settotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}
	
	public void adicionar(Integer quantity) {
		this.quantity += quantity;
		Double novoValor = this.product.getPrice()*quantity;
		Double novoTotal = this.totalValue + novoValor;
		this.totalValue = novoTotal;
	}
	
	public void remover(Integer quantity) {
		this.quantity -= quantity;
		Double novoValor = this.product.getPrice() * quantity;
		this.totalValue = this.totalValue - novoValor;
	}

	@Override
	public String toString() {
		return "productquantity [product=" + product + ", quantity=" + quantity + ", totalValue=" + totalValue
				+ "]";
	}
}
