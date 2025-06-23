package com.ochcdevelopment.cartshops.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

    //esto se lee de muchos a muchos example muchos roles para muchos usuarios
    @ManyToMany(mappedBy = "roles")
    private Collection<User>users = new HashSet<>();

}
