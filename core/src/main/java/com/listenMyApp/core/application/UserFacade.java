package com.listenMyApp.core.application;

import com.listenMyApp.core.domain.User;
import com.listenMyApp.core.domain.UserException;

/**
 * Contem os servicos relacionados a usuario.
 * 
 * @author Marco Rojo
 * Data: 14/02/2010
 */
public interface UserFacade {

	/**
	 * Executa a logica necessaria para fazer o login do usuario no sistema.
	 * @param email email do usuario
	 * @param password senha do usuario
	 * @return User com as informacoes do usuario logado.
	 * @throws UserException caso nao encontre o usuario, ou senha fornecida diferente da senha cadastrada na base de dados.
	 */
	User login(String email, String password) throws UserException;

	
	/**
	 * Cria uma nova conta de usuario, a partir de um User informado.
	 * 
	 * @param name nome do novo usuario
	 * @param email email do novo usuario
	 * @param password senha do novo usuario
	 * @param idAccount codigo da conta do novo usuario
	 * @param language lingua de preferencia do usuario
	 * @param idTimeZone identificador do timezone do usuario.
	 * @param notify indicando que o usuario quer ser notificado de novos eventos por email.
	 * @return novo usuario criado e persistido.
	 * @throws UserException caso nao consiga fazer a persistencia do novo usuario.
	 */
	User create(String name, String email, String password, Long idAccount, String language, String idTimeZone, Boolean notify) throws UserException;
	
	/**
	 * Ativa o usuario, deixando-o apto a utilizar o sistema.
	 * @param userKey chave do usuario
	 * @return user ativado.
	 * @throws UserException caso ocorra algum erro.
	 */
	User activate(String userKey) throws UserException;


	/**
	 * Permite a alteracao de senha pelo usuario.
	 * @param email do usuario que deseja alterar a senha.
	 * @param oldPassword senha antiga.
	 * @param newPassword nova senha.
	 * @throws UserException caso nao consiga alterar a senha.
	 */
	void changePassword(String email, String oldPassword, String newPassword) throws UserException;


	/**
	 * Cria uma nova senha para o email informado, enviando email com link para alterar a senha.
	 * @param email do usuario que esqueceu a senha. 
	 * @throws UserException caso nao consiga resetar a senha do usuario.
	 */
	void forgetPassword(String email) throws UserException;


	/**
	 * Atualiza os dados de um dado usuario.
	 * @param userKey chave do usuario 
	 * @param name nome do usuario alterado.
	 * @param language language do usuario alterada.
	 * @param idTimeZone timezone do usuario alterado.
	 * @param notify indicando que o usuario quer ser notificado de novos eventos por email.
	 */
	void alter(String userKey, String name, String language,
			String idTimeZone, Boolean notify) throws UserException;

}
