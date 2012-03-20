package com.listenMyApp.core.application;

import java.util.List;

import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventException;
import com.listenMyApp.core.domain.EventStatus;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.core.domain.UserException;


/**
 * Contem os servicos relacionados a Events lancados pelas aplicacoes.
 * 
 * @author Marco Rojo
 * Data: 15/02/2010
 */
public interface EventFacade {

	/**
	 *Cria um novo Evento, para um dado projeto.
	 *
	 * @param projectKey projeto onde sera criado o evento.
	 * @param environment ambiente onde ocorreu o evento.
	 * @param apiVersion versao da API utilizada para envio do evento.
	 * @param clientName nome da aplicacao cliente que esta enviando o evento.
	 * @param clientVersion versao da aplicacao cliente que esta enviando o evento.
	 * @param category categoria do evento.
	 * @param message mensagem do evento.
	 * @param className classe onde foi registrado o evento.
	 * @param fileName nome do arquivo onde foi registrado o evento.
	 * @param lineNumber linha onde foi gerado o evento.
	 * @param methodName nome do metodo onde foi gerado o evento.
	 * @param trace stacktrace do evento gerado.
	 * @return
	 */
	Event create(String projectKey, String environment,
			String apiVersion, String clientName, String clientVersion,
			String category, String message, String className,
			String fileName, String lineNumber, 
			String methodName, String trace) throws EventException, ProjectException;

	/**
	 * Encerra o evento 
	 * @param userKey chave do usuario.
	 * @param eventId id do evento a ser encerrado.
	 * @return evento com status closed.
	 * @throws EventException caso nao encontre o evento.
	 * @throws UserException caso nao encontre o usuario.
	 */
	Event close(String userKey, Long id) throws EventException, UserException;

	
	/**
	 * Busca os ultimos eventos abertos de um determinado usuario. 
	 * @param userKey chave do usuario.
	 * @return lista de eventos com status opened.
	 * @throws EventException caso nao encontre o evento.
	 * @throws UserException caso nao encontre o usuario.
	 */
	List<Event> getLastOpened(String userKey) throws EventException, UserException;


	/**
	 * Busca todos os eventos de um dado projectKey. 
	 * @param projectKey chave do projeto.
	 * @return lista de eventos com status opened.
	 * @throws EventException caso nao encontre o evento.
	 * @throws UserException caso nao encontre o usuario.
	 */
	List<Event> getByProjectKey(String projectKey) throws ProjectException;

	
	/**
	 * Calcula quantos eventos estao abertos para um dado projectKey.
	 * @param projectId identificador do projeto.
	 * @return quantidade de eventos abertos.
	 * @throws EventException caso ocorra algum erro.
	 */
	Long getCntOpenedEvents(Long projectId) throws EventException;

	/**
	 * Lista os eventos de um determinado projeto para um dado status.
	 * @param projectId identificador do projeto.
	 * @param eventStatus status do evento.
	 * @return lista de eventos.
	 * @throws EventException caso ocorra algum erro.
	 */
	List<Event> filterByStatus(Long projectId, EventStatus ... eventStatus) throws EventException;

	/**
	 * Lista todos os eventos para um determinado projectId. 
	 * @param projectId identificador do projeto.
	 * @return lista de eventos encontrados.
	 * @throws ProjectException caso ocorra algum erro.
	 */
	List<Event> getByProjectId(Long projectId) throws ProjectException;

	/**
	 * Busca os detalhes de um evento para um dado eventId.
	 * @param eventId identificador do evento que se deseja buscar.
	 * @return dados do evento localizado.
	 * @throws EventException caso ocorra algum erro. 
	 */
	Event get(Long eventId) throws EventException;

}
