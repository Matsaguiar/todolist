package br.com.matsaguiar.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_user")
public class UserModel {

    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(unique=true)
    private String userName;
    
    private String name;
    
    // @Column(name="password")
    private String password;

    private byte[] passwordEncoded;
}
