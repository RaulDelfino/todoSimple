package com.RaulDelfino.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RaulDelfino.todosimple.models.User;




@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // extends JpaRepository ja pode ser ultilizada e assim podemos usar todos os metodos do banco de dados
}
