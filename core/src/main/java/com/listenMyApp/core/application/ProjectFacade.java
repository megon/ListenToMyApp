package com.listenMyApp.core.application;

import java.util.List;

import com.listenMyApp.core.domain.Project;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.core.domain.UserException;

/**
 * Contem os servicos relacionados a projeto.
 * 
 * @author Marcelo Menezes
 * Data: 15/02/2010
 */
public interface ProjectFacade {

	/**
	 * Cria um novo projeto para um Usu‡rio informado
	 * 
	 * @param userKey chave do usuario administrador do projeto.
	 * @param projectName nome do projeto.
	 * @return novo projeto criado e persistido.
	 * @throws ProjectException caso nao consiga fazer a persistencia do novo projeto.
	 * @throws UserException caso nao consiga encontrar o usu‡rio pela userKey.
	 */
	Project create(String userKey, String projectName) throws ProjectException, UserException;

	
	/**
	 * Faz a exclusao de um projeto na base de dados.
	 * 
	 * @param userKey chave do usuario administrador do projeto.
	 * @param projectId id do projeto
	 */
	void delete(String userKey, Long projectId) throws ProjectException, UserException;

	
	/**
	 * Busca os detalhes de um dado projeto.
	 * @param userKey chave do usuario.
	 * @param projectId id do projeto.
	 * @return projeto com os eventos associados.
	 * @throws ProjectException caso não consiga localizar o projeto.
	 * @throws UserException caso nao consiga localizar o usuario.
	 */
	Project get(String userKey, Long projectId) throws ProjectException, UserException;


	void addUserToProject(String userKey, Long projectId, String emailUserToAdd) throws ProjectException, UserException;


	/**
	 * Busca todos projetos cadastrados para um dado userKey.
	 * @param userKey chave do usuario.
	 * @return Lista de projetos associados ao uerKey.
	 * @throws ProjectException caso não consiga localizar o projeto.
	 * @throws UserException caso nao consiga localizar o usuario.
	 */
	List<Project> getByUserKey(String userKey) throws UserException;


	/**
	 * Gera uma nova chave do projeto
	 * @param userKey chave do usuario.
	 * @param projectId id do projeto
	 * @return nova chave gerada
	 * @throws UserException caso nao localize o usuario
	 * @throws ProjectException caso nao localize o projeto.
	 */
	String generateKey(String userKey, Long projectId) throws UserException, ProjectException;


	/**
	 * Altera informacoes de um projeto
	 * @param userKey chave do usuario.
	 * @param projectId id do projeto
	 * @param projectName id do projeto
	 * @param projectKey id do projeto
	 * @throws UserException caso nao localize o usuario
	 * @throws ProjectException caso nao localize o projeto.
	 */
	void alter(String userKey, Long projectId, String projectName,
			String projectKey) throws UserException, ProjectException ;

}
