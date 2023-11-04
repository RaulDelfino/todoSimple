package com.RaulDelfino.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.RaulDelfino.todosimple.models.User;
import com.RaulDelfino.todosimple.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired // Serve como Contrutor da nossa classe service
    private UserRepository userRepository;


    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
            "Usuário nao encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
        // orElseThrow() - Retorna o usuario se ele estiver Preenchido
        // RuntimeException - retorna um erro mas nao para a aplicacão
    }

    @Transactional // Garantir que ou salva tudo ou não salva nada, util para alterar ou criar dados
    public User create(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional 
    public User update(User obj){
        User newObj = findById(obj.getId());

        newObj.setPassword(obj.getPassword());

        return this.userRepository.save(newObj);
    }

    public void delete(long id){
        findById(id);

        try{
            this.userRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas!");
        }

    }

}
