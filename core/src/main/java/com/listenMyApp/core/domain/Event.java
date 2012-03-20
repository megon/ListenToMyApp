package com.listenMyApp.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.listenMyApp.core.domain.validator.CheckCategory;
import com.listenMyApp.core.domain.validator.CheckEnvironment;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Classe de dominio responsavel por encapsular todas as informacoes de um evento lancado por um usuario do listenMyApp.<br>
 * Tem relacionamento com a tabela TB_EVENT.
 * 
 * @author Marco Rojo
 * Data: 15/02/2010
 */
@Entity
@Table(name="TB_EVENT")
@XStreamAlias("event")
@NamedQueries( {
		@NamedQuery(name = "Event.lastOpened", query = "SELECT event " 
														+ "FROM Event event " 
														+ ", Project project " 
														+ ", User user " 
														+ "INNER JOIN project.events as eventsList "  
														+ "INNER JOIN project.users as usersList "
														+ "WHERE event.status = :idStatus "
														+ "AND user.id = :idUser "
														+ "AND event = eventsList "
														+ "AND user = usersList "
														+ "ORDER BY event.eventDate desc")
		, @NamedQuery(name = "Event.cntOpenedEvents", query = "SELECT count(event) "
														+ "FROM Event event "
														+ "WHERE event.project.id = :projectId "
														+ "AND event.status = :idStatus")
		, @NamedQuery(name = "Event.filterByStatus", query = "SELECT event "
															+ "FROM Event event "
															+ "WHERE event.project.id = :projectId "
															+ "AND event.status = :idStatus "
															+ "ORDER BY event.eventDate desc")
														
})
public class Event {
	
	
	public Event(){}
	
	public Event(String apiVersion, String clientName, String clientVersion
				, String environment, String category, String message, Date eventDate, String className
				, String fileName, String lineNumber, String methodName, String trace
				, EventStatus status){
		this.environment = environment;
		this.apiVersion = apiVersion;
		this.clientName = clientName;
		this.clientVersion = clientVersion;
		this.message = message;
		this.category = category;
		this.eventDate = eventDate;
		this.className = className;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.methodName = methodName;
		this.trace = trace;
		this.status = status;
	}
	
	public Event(String projectKey, String apiVersion, String clientName, String clientVersion 
			, String environment, String category, String message, Date eventDate, String className
			, String fileName, String lineNumber, String methodName, String trace){
		this.projectKey = projectKey;
		this.environment = environment;
		this.apiVersion = apiVersion;
		this.clientName = clientName;
		this.clientVersion = clientVersion;
		this.category = category;
		this.message = message;
		this.eventDate = eventDate;
		this.className = className;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.methodName = methodName;
		this.trace = trace;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_EVENT")
	private Long id;
	
	@Transient
	@XStreamAlias("projectKey")
	@NotNull(message="error.event.projectKey.cannot.be.null")
	private String projectKey;
	
	@Column(name="DS_ENVIRONMENT")
	@XStreamAlias("environment")
	@CheckEnvironment
	@NotNull(message="error.event.environment.cannot.be.null")
	private String environment;
	
	@Column(name="DS_CATEGORY")
	@XStreamAlias("category")
	@Enumerated(EnumType.ORDINAL)
	@CheckCategory
	private String category;
	
	@Column(name="DS_API_VERSION")
	@XStreamAlias("apiVersion")
	private String apiVersion;
	
	@Column(name="DS_CLIENT_NAME")
	@XStreamAlias("clientName")
	private String clientName;
	
	@Column(name="DS_CLIENT_VERSION")
	@XStreamAlias("clientVersion")
	private String clientVersion;
	
	
	@Column(name="DS_MESSAGE")
	@XStreamAlias("message")
	@NotNull(message="error.event.message.cannot.be.null")
	private String message;
	
	@Column(name="DT_EVENT")
	@Temporal(TemporalType.TIMESTAMP)
	@XStreamAlias("eventDate")
	private Date eventDate;
	
	@Column(name="NM_CLASS")
	@XStreamAlias("className")
	private String className;
	
	@Column(name="NM_FILE")
	@XStreamAlias("fileName")
	private String fileName;
	
	@Column(name="NR_LINE")
	@XStreamAlias("lineNumber")
	private String lineNumber;
	
	@Column(name="NM_METHOD")
	@XStreamAlias("methodName")
	private String methodName;
	
	@Column(name="DS_TRACE")
	@XStreamAlias("trace")
	private String trace;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROJECT")
	@XStreamOmitField
	private Project project;
	
	@Column(name="ID_EVENT_STATUS")
	@Enumerated(EnumType.ORDINAL)
	@XStreamOmitField
	private EventStatus status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}	
	
	
	
}
