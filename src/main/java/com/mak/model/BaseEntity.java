package com.mak.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @JsonIgnore
    private boolean del = false;

}
