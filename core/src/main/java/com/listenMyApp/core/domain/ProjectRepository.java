package com.listenMyApp.core.domain;


public interface ProjectRepository {

	void save(Project project);
	
	/**
	 * Busca um projeto na base de dados atraves de sua key.
	 * @param projectKey chave do projeto.
	 * @return as informacoes do projeto selecionado.
	 *  @throws ProjectException caso nao encontre o projeto.
	 */
	Project getByKey(String projectKey) throws ProjectException;	
	
	
	/**
	 * Localiza um projeto a partir de seu id (chave primaria do banco de dados).
	 * @param id id do projeto
	 * @return as informacoes do projeto encontrado.
	 * @throws ProjectException caso nao encontre o projeto.
	 */
	Project getById(Long id) throws ProjectException;
	
	/**
	 * Localiza um projeto a partir da chave do usuario mais o id de um projeto(chave primaria do banco de dados).
	 * @param userKey chave do usuario.
	 * @param id id do projeto
	 * @return as informacoes do projeto encontrado.
	 * @throws ProjectException caso nao encontre o projeto.
	 */
	Project getByUserKeyProjectId(String userKey, Long id) throws ProjectException;
	
	
	/**
	 * Exclui o projeto da base de dados.
	 * 
	 * @param project projeto a ser removido.
	 * @throws ProjectException caso ocorra algum erro na operacao.
	 */
	void remove(Project project) throws ProjectException;

}
