package com.listenMyApp.core.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Classe de dominio responsavel por encapsular todas as informacoes e regras de negocio de uma conta.<br>
 * Tem relacionamento com a tabela TB_ACCOUNT.
 * 
 * @author Marco Rojo
 * Data: 14/02/2010
 */
@Entity
@Table(name="TB_ACCOUNT")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ACCOUNT")
	private Long id;
	
	@Column(name="DS_ACCOUNT")
	private String account;
	
	@Column(name="NR_EXCEPTIONS_MINUTE")
	private Integer numberOfExceptionsPerMinute;
	
	@Column(name="VL_SUBSCRIPTION")
	private BigDecimal valueSubscription;
	
	@Column(name="QT_PROJECTS")
	private Integer numberOfProjectsAlowed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getNumberOfExceptionsPerMinute() {
		return numberOfExceptionsPerMinute;
	}

	public void setNumberOfExceptionsPerMinute(Integer numberOfExceptionsPerMinute) {
		this.numberOfExceptionsPerMinute = numberOfExceptionsPerMinute;
	}

	public BigDecimal getValueSubscription() {
		return valueSubscription;
	}

	public void setValueSubscription(BigDecimal valueSubscription) {
		this.valueSubscription = valueSubscription;
	}

	public Integer getNumberOfProjectsAlowed() {
		return numberOfProjectsAlowed;
	}

	public void setNumberOfProjectsAlowed(Integer numberOfProjectsAlowed) {
		this.numberOfProjectsAlowed = numberOfProjectsAlowed;
	}
	
	
	

}
