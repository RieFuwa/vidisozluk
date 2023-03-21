package com.bkabatas.ssozlukproject.model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "postTypes")
public class PostType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String postTypeName;
}
