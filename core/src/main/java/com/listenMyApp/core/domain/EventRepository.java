package com.listenMyApp.core.domain;

import java.util.List;

/**
 * Repositorio responsavel por fornecer dados de eventos.<br>
 * Faz a ponte entre a classe {@link Event} e a tabela TB_EVENT.
 * 
 * @author Marco Rojo
 * Data: 19/02/2010
 */
public interface EventRepository {

	/**
	 * Persiste em banco de dados o novo evento.
	 * @param event evento a ser persistido no banco de dados.
	 *  @throws EventException caso nao seja possivel fazer a persistencia em banco.
	 */
	void save(Event event) throws EventException;
	
	
	/**
	 * Localiza um evento a partir de seu id (chave primaria do banco de dados).
	 * @param id id do evento.
	 * @return as informacoes do evento encontrado.
	 * @throws EventException caso nao encontre o evento.
	 */
	Event getById(Long id) throws EventException;


	/**
	 * Lista todos os eventos com status opened para um determinado usuario.
	 * @param id id do usuario.
	 * @return lista de eventos encontrados.
	 */
	List<Event> getByLastOpened(Long id) throws EventException;


	Long getCntOpenedEvents(Long projectId) throws EventException;


	List<Event> filterByStatus(Long projectId, EventStatus eventStatus) throws EventException;	

}
