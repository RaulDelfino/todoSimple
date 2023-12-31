package com.RaulDelfino.todosimple.models;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.RaulDelfino.todosimple.models.enums.ProfileEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;




@Entity
@Table(name =User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
//@Data
public class User {

    public interface CreateUser {}

    public interface UpdateUser {}

    public static final String TABLE_NAME ="user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment
    @Column(name = "id", unique = true)
    private Long id;

    
    //spring-boot-starter-validation
   
    @Column(name  ="username", length = 100, nullable = false, unique = true)
    @NotNull(groups  =  CreateUser.class) // nao pode ser nulo quando for criar o usuario
    @NotEmpty(groups  =  CreateUser.class) // nao pode ser vazia
    @Size(groups  =  CreateUser.class, min =2,  max=100)
    private String username;

    
    @JsonProperty(access = Access.WRITE_ONLY) // quando retornar o usuario n vai retornar a senha junto
    @Column(name = "password", length = 60, nullable=false)
    @NotNull(groups  =  {CreateUser.class, UpdateUser.class}) // nao pode ser nulo quando for criar ou atualizar a senha
    @NotEmpty(groups  =  {CreateUser.class, UpdateUser.class})
    @Size(groups  =  {CreateUser.class, UpdateUser.class}, min=8, max =60)
    private String password;

    @OneToMany(mappedBy ="user") // um usuario pode ter varias taks, e quem esta mapeando é a variavel user, la da Task model
        @JsonProperty(access =  Access.WRITE_ONLY) // quando buscar um usuario não vai retornar
    private List<Task> tasks  = new ArrayList<Task>();

    //Set serve para não repetir na lista
    @ElementCollection(fetch = FetchType.EAGER) // serpre que buscar o usuario do banco vai buscar os perfis junto
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)// nao retorna pro usuario o perfil dele
    @CollectionTable(name = "user_profile") // Vira uma nova tabela para guadar o tipo do usuario
    @Column(name = "profile" , nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles(){
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profile){
        this.profiles.add(profile.getCode());
    }


}
