package com.example.enkatapp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();



    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys = new ArrayList<>();

    // getters och setters

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }

    // En hjälpmetod för att enkelt lägga till en Survey till en User
    public void addSurvey(Survey survey) {
        surveys.add(survey);
        survey.setUserEntity(this);
    }

    // En hjälpmetod för att enkelt ta bort en Survey från en User
    public void removeSurvey(Survey survey) {
        surveys.remove(survey);
        survey.setUserEntity(null);
    }
}

