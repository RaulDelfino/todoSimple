package com.RaulDelfino.todosimple.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RaulDelfino.todosimple.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // TRAZER LISTA DE TAREFAS DE UM USUARIO

    // 1º Forma de Buscar Dados no banco 
    // procure o usuario dentro de id =  findByUser_id(Long id) || Task --> User --> Id
    List<Task> findByUser_id(long id);


    // Optional - Se não existir = vazio 
    // Optional<Task> findById(Long id);


    // 2º Forma de trazer dados no Banco, com query Spring Boot
    // @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    // List<Task> findByUser_id(@Param("id") Long id);


    // 3º Forma de trazer dados no Banco, com Query nativa do SQL
    // @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    // List<Task> findByUser_Id(@Param("id") Long id);
}
