package com.listenMyApp.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.listenMyApp.core.util.ManageKeyUtil;

/**
 * Classe de dominio responsavel por encapsular todas as informacoes de um usuario do listenMyApp.<br>
 * Tem relacionamento com a tabela TB_USER.
 * 
 * @author Marco Rojo
 * Data: 14/02/2010
 */
@Entity
@Table(name="TB_USER")
@NamedQueries( {
		@NamedQuery(name = "User.key", query = "SELECT user FROM User user LEFT OUTER JOIN FETCH user.projects WHERE user.key = :key"),
		@NamedQuery(name = "User.email", query = "SELECT user FROM User user LEFT OUTER JOIN FETCH user.projects WHERE user.email = :email")
//		@NamedQuery(name = "User.projects", query = "SELECT projects FROM User user WHERE user.email = :email")
})
public class User {
	
	private static final String PRIVATE_KEY = "UserDomain";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_USER")
	private Long id;
	
	@Column(name="DS_NAME")
	@NotNull(message="error.user.nameNull")
	private String name;
	
	@Column(name="DS_PASSWORD")
	@NotNull(message="error.user.passwordNull")
	@Size(min=4,message="error.user.minPasswordSize")
	private String password;
	
	@Column(name="DS_KEY")
	private String key;
	
	@Column(name="DS_EMAIL", unique=true)
	@Email(message="error.user.invalidEmail")
	private String email;
	
	@Column(name="FL_NOTIFY")
	private Boolean notify;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ID_ACCOUNT")
	private Account account;
	
	@Column(name="FL_ACTIVE")
	private Boolean active;
	
	@ManyToMany
	@JoinTable(name = "TB_USER_PROJECT",
			joinColumns = { @JoinColumn(name = "ID_USER") },
			inverseJoinColumns = { @JoinColumn(name = "ID_PROJECT") })
	private List<Project> projects;
	
	@Column(name="DS_LANGUAGE")
	@Enumerated(EnumType.STRING)
	private Language language;
	
	@Column(name="ID_TIMEZONE")
	@NotNull
	private String idTimeZone;
	
	@Column(name="DT_REGISTER")
	@Temporal(TemporalType.TIMESTAMP)
	private Date register;
	
	public User(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	};
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String createKey(){
		this.key = ManageKeyUtil.createKey(getEmail().concat(new Date().toString()), PRIVATE_KEY); 
		return key;
	}
	
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Boolean isNotify() {
		return notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(String language) throws UserException {
		try{
			this.language = Language.valueOf(language);
		}
		catch (Exception ex){
			throw new UserException("error.user.invalidLanguage");
		}
	}

	public String getIdTimeZone() {
		return idTimeZone;
	}

	public void setIdTimeZone(String idTimeZone) {
		this.idTimeZone = idTimeZone;
	}

	public void setRegister(Date register) {
		this.register = register;
	}
	
	public Date getRegister() {
		return register;
	}
}
