package com.listenMyApp.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.listenMyApp.core.util.ManageKeyUtil;

/**
 * Classe de dominio responsavel por encapsular todas as informacoes de um projeto do listenMyApp.<br>
 * Tem relacionamento com a tabela TB_PROJECT.
 * 
 * @author Marcelo Menezes
 * Data: 15/02/2010
 */

@Entity
@Table(name="TB_PROJECT")
@NamedQueries( {
		@NamedQuery(name = "Project.userKeyProjectId", query = "SELECT project FROM Project project INNER JOIN project.users user WHERE user.key = :userKey AND project.id = :id")
		, @NamedQuery(name = "Project.key", query = "SELECT project FROM Project project LEFT OUTER JOIN FETCH project.events WHERE project.key = :key")
		, @NamedQuery(name = "Project.id", query = "SELECT project FROM Project project LEFT OUTER JOIN FETCH project.events WHERE project.id = :id")
})
public class Project {
	private static final String PRIVATE_KEY = "ProjectDomain";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PROJECT")
	private Long id;
	
	@Column(name="DS_NAME")
	@NotNull
	private String name;
	
	@Column(name="DS_KEY")
	@NotNull
	private String key;

	@OneToMany(mappedBy="project", cascade={javax.persistence.CascadeType.REMOVE})
	@OrderBy("eventDate DESC")
	private List<Event> events;

	@ManyToMany(mappedBy="projects")
	private List<User> users;

	@Transient
	private Long cntOpenedEvents;
	
	@Transient
	private Long cntClosedEvents;
	
	@Transient Long cntTotalEvents;
	
	
	public Project(){}


	public Project(String name) {
		this.name = name;
	}


	public void setEvents(List<Event> events) {
		this.events = events;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public String createKey() {
		this.key = ManageKeyUtil.createKey(getName().concat(new Date().toString()), PRIVATE_KEY); 
		return key;
	}


	public List<Event> getEvents() {
		return events;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public Long getCntOpenedEvents() {
		return cntOpenedEvents;
	}


	public void setCntOpenedEvents(Long cntOpenedEvents) {
		this.cntOpenedEvents = cntOpenedEvents;
	}


	public Long getCntClosedEvents() {
		return cntClosedEvents;
	}


	public void setCntClosedEvents(Long cntClosedEvents) {
		this.cntClosedEvents = cntClosedEvents;
	}


	public Long getCntTotalEvents() {
		return cntTotalEvents;
	}


	public void setCntTotalEvents(Long cntTotalEvents) {
		this.cntTotalEvents = cntTotalEvents;
	}
	
	
	
}
