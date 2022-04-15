package com.list.contact.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty
    private String name = "";

    @NotEmpty
    private String photoUrl = "";

}
