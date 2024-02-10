package domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import domain.interfaces.IPersistence;

@Entity
@Table(name = "tb_sale")
public class Sale implements IPersistence {

	public enum Status {
		INICIADA, CONCLUIDA, CANCELADA;

		public static Status getByName(String value) {
			for (Status status : Status.values()) {
				if (status.name().equals(value)) {
					return status;
				}
			}
			return null;
		}
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sale_seq")
	@SequenceGenerator(name="prod_sale", sequenceName="sq_sale", initialValue = 1, allocationSize = 1)
	private Long id;

	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@ManyToOne
	@JoinColumn(name = "id_cliente_fk", 
		foreignKey = @ForeignKey(name = "fk_sale_cliente"), 
		referencedColumnName = "id", nullable = false
	)
	private Client client;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
	private Set<ProductQuantity> products;

	@Column(name = "total", nullable = false)
	private Double total;

	@Column(name = "Date", nullable = false)
	private Instant date;

	@Column(name = "status", nullable = false)
	private Status status;

	public Sale(String code, Client client, Instant date, Status status) {
		this.products = new HashSet<>();
		this.code = code;
		this.client = client;
		this.date = date;
		this.status = status;
	}

	public Sale() {
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

	public Client getclient() {
		return client;
	}

	public void setclient(Client client) {
		this.client = client;
	}

	public Instant getdate() {
		return date;
	}

	public void setdate(Instant date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<ProductQuantity> getproducts() {
		return products;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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
		Sale other = (Sale) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Sell [code=" + code + ", client=" + client + ", products=" + products.toArray().toString()
				+ ", total=" + total + ", date=" + date + ", status=" + status + "]";
	}

	public void adicionarProduto(Product produto, Integer quantidade) {
		validarStatus();
		Optional<ProductQuantity> op = products.stream()
				.filter(filter -> filter.getproduct().getCode().equals(produto.getCode())).findAny();

		if (op.isPresent()) {
			ProductQuantity produtpQtd = op.get();
			produtpQtd.adicionar(quantidade);
		} else {
			ProductQuantity prod = new ProductQuantity();
			prod.setproduct(produto);
			prod.adicionar(quantidade);
			products.add(prod);
		}
		recalcularValorTotalVenda();
	}

	private void validarStatus() {
		if (this.status == Status.CONCLUIDA) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
		}
	}

	public void removerProduto(Product produto, Integer quantidade) {
		validarStatus();
		Optional<ProductQuantity> op = products.stream()
				.filter(filter -> filter.getproduct().getCode().equals(produto.getCode())).findAny();

		if (op.isPresent()) {
			ProductQuantity produtpQtd = op.get();
			if (produtpQtd.getquantity() > quantidade) {
				produtpQtd.remover(quantidade);
				recalcularValorTotalVenda();
			} else {
				products.remove(op.get());
				recalcularValorTotalVenda();
			}

		}
	}

	public void removerTodosproducts() {
		validarStatus();
		products.clear();
		total = 0.0;
	}

	public Integer getQuantidadeTotalproducts() {
		int result = products.stream().reduce(0,
				(partialCountResult, prod) -> partialCountResult + prod.getquantity(), Integer::sum);
		return result;
	}

	public void recalcularValorTotalVenda() {
		validarStatus();
		double valorTotal = 0.0;
		for (ProductQuantity prod : this.products) {
			valorTotal = valorTotal + prod.gettotalValue();
		}
		this.total = valorTotal;
	}

	public void setproducts(Set<ProductQuantity> products) {
		this.products = products;
	}
}
