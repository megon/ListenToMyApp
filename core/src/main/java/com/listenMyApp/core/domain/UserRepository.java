package com.listenMyApp.core.domain;

/**
 * Repositorio responsavel por fornecer dados de usuarios.<br>
 * Faz a ponte entre a classe {@link User} e a tabela TB_USER.
 * 
 * @author Marco Rojo
 * Data: 14/02/2010
 */
public interface UserRepository {

	/**
	 * Faz a busca no banco de dados atraves de um login fornecido. Caso encontre o usuario pelo login,
	 * compara a senha fornecida com a encontrada no banco de dados. 
	 * @param email email do usuario
	 * @param password senha informada
	 * @return User - informacoes do usuario
	 * @throws UserException caso nao encontre o usuario, ou senha fornecida diferente da senha cadastrada na base de dados.
	 */
	User login(String email, String password) throws UserException;

	/**
	 * Persiste em banco de dados o novo usuario.
	 * @param user usuario a ser persistido no banco de dados.
	 * @return as informacoes do novo usuario criado.
	 *  @throws UserException caso nao seja possivel fazer a persistencia em banco.
	 */
	void save(User user) throws UserException;

	/**
	 * Busca um usu‡rio no banco de dados atraves pela userKey.
	 * @param userKey chave do usu‡rio.
	 * @return as informacoes do novo usuario criado.
	 *  @throws UserException caso nao encontre o usuario.
	 */
	User getByKey(String userKey) throws UserException;
	
	User getByEmail(String email) throws UserException;	
}

