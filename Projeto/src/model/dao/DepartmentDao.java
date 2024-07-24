package model.dao;


import java.util.List;

import model.entities.Department;

/**
 * Interface que define as operações de acesso a dados para a entidade Department.
 */

public interface DepartmentDao {

	
	/**
     * Cria a tabela de Department no banco de dados.
     * 
     */
	void createTable() ;
	
	 /**
     * Insere um novo Department no banco de dados.
     * 
     * @param obj o objeto Department a ser inserido
     */
	void insert(Department obj);
	
    /**
     * Atualiza um Department existente no banco de dados.
     * 
     * @param obj o objeto Department com as atualizações a serem aplicadas
     */
	void update(Department obj);

	 /**
     * Exclui um Department pelo ID.
     * 
     * @param id o ID do Department a ser excluído
     */
	void deleteById(Integer id);

	 /**
     * Encontra um Department pelo ID.
     * 
     * @param id o ID do Department a ser encontrado
     * @return o Department correspondente ao ID fornecido, ou null se não encontrado
     */
	Department findById(Integer id);

	 /**
     * Encontra todos os Departments.
     * 
     * @return uma lista de todos os Departments
     */
	List<Department> findAll();

}
