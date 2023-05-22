package com.mak.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    // GenerationType.IDENTITY : mostly used with database identity column
    // (autoincrement) column, Note this will DISABLES batch updates.
    private Long id;

    @JsonIgnore
    private boolean del = false;

}
