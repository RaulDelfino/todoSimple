package com.RaulDelfino.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.RaulDelfino.todosimple.models.Task;
import com.RaulDelfino.todosimple.models.User;
import com.RaulDelfino.todosimple.repositories.TaskRepository;


@Service
public class TaskService {
    
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

     public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(()-> new RuntimeException(
            "Tarefa não encontrada id " + id + ", Tipo: " + Task.class.getName()
        ));

     }

     @Transactional
     public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());

        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
     }

     @Transactional
     public Task update(Task obj){
        Task newObj = this.findById(obj.getId()); // pesquisar objetp que voce quer atualizar
        newObj.setDescription(obj.getDescription()); // atualiza o novo objeto com a descrição do objeto passado pelo usuario
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);

        try{
            this.taskRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException(
                "Não é possivel excluir pois há entidades relacionadas!"
                );
        }
    }

}
