package domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import domain.interfaces.IPersistence;

@Entity
@Table(name = "tb_product")
public class Product implements IPersistence {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sq")
	@SequenceGenerator(name = "product_sq", sequenceName = "sq_product", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "amountStock", nullable = false)
	private Double amountStock;
	
	public Product(String code, String name, String description, Double price, Integer quantity) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.amountStock = price*quantity;
	}	 
	
	public Product(Long id, String code, String name, String description, Double price, Integer quantity) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.amountStock = price*quantity;
	}

	public Product() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
		setAmountStock();
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
		setAmountStock();
	}
	
	public Double getAmountStock() {
		return amountStock;
	}
	
	public void setAmountStock() {
		this.amountStock = this.price*this.quantity;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", name=" + name + ", description=" + description + ", price="
				+ price + ", quantity=" + quantity + ", amountStock=" + amountStock + "]";
	}

}
