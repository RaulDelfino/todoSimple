package com.RaulDelfino.todosimple.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.RaulDelfino.todosimple.models.User;
import com.RaulDelfino.todosimple.models.enums.ProfileEnum;
import com.RaulDelfino.todosimple.repositories.UserRepository;
import com.RaulDelfino.todosimple.services.exceptions.DataBindingViolationException;
import com.RaulDelfino.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired // Serve como Contrutor da nossa classe service
    private UserRepository userRepository;


    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
            "Usuário nao encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
        // orElseThrow() - Retorna o usuario se ele estiver Preenchido
        // RuntimeException - retorna um erro mas nao para a aplicacão
    }

    @Transactional // Garantir que ou salva tudo ou não salva nada, util para alterar ou criar dados
    public User create(User obj){
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword())); // criptografar password antes de savar
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet())); // Garamtir que quando o usuario for criado, vai ser lançado com o code 2 - user
        obj = this.userRepository.save(obj); // usuario salvo com perfil de usuario e senha criptografada
        return obj;
    }

    @Transactional 
    public User update(User obj){
        User newObj = findById(obj.getId());

        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));

        return this.userRepository.save(newObj);
    }

    public void delete(long id){
        findById(id);

        try{
            this.userRepository.deleteById(id);
        }catch(Exception e){
            throw new DataBindingViolationException("Não é possivel excluir pois há entidades relacionadas!");
        }

    }

}
