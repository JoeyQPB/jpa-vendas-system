package domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import annotations.UniqueValue;
import domain.interfaces.IPersistence;

@Entity
@Table(name = "tb_client")
public class Client implements IPersistence {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sq")
	@SequenceGenerator(name = "client_sq", sequenceName = "sq_client", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "cpf", nullable = false, unique = true)
	@UniqueValue()
	private Long cpf;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "celNumber", nullable = false)
	private Long celNumber;
	
	@Embedded
	private Address adress;
	
	@Column(name = "createdAt", nullable = false)
	private Instant createdAt;

	public Client() {}

	public Client(Long id, Long cpf, String name, Long celNumber, Address adress, Instant createdAt) {
		this.id = id;
		this.cpf = cpf;
		this.name = name;
		this.celNumber = celNumber;
		this.adress = adress;
		this.createdAt = createdAt;
	}

	public Client(Long cpf, String name, Long celNumber, Address adress, Instant createdAt) {
		this.cpf = cpf;
		this.name = name;
		this.celNumber = celNumber;
		this.adress = adress;
		this.createdAt = createdAt;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCelNumber() {
		return celNumber;
	}

	public void setCelNumber(Long celNumber) {
		this.celNumber = celNumber;
	}

	public Address getAdress() {
		return adress;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	} 
}
