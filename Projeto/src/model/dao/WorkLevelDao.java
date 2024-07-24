package model.dao;

import java.util.List;

import model.entities.WorkLevel;

/**
 * Interface que define as operações de acesso a dados para a entidade
 * WorkLevel.
 */
public interface WorkLevelDao {

	/**
	 * Cria a tabela de WorkLevel no banco de dados.
	 * 
	 */
	void createTable();

	/**
	 * Insere os níveis de trabalho iniciais na tabela de WorkLevel.
	 * 
	 */
	void insertInititalWorkLevels();

	/**
	 * Encontra todos os níveis de trabalho.
	 * 
	 * @return uma lista com todos os níveis de trabalho
	 */
	List<WorkLevel> findAll();

}
