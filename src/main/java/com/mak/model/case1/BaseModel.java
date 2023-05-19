package com.mak.model.case1;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseModel implements Serializable {

    @Id
    private String id;

    @CreatedDate
    private Date created;

    @LastModifiedDate
    private Date updated;

    @JsonIgnore
    private boolean del = false;

    // @Override
    // public boolean isNew() {
    // return false;
    // }

    // public BaseModel() {
    // // this.
    // }

    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID().toString();
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

}
