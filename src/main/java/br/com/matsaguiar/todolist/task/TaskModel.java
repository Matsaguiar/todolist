package br.com.matsaguiar.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_task")
public class TaskModel{

    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private String title;
    
    private String description;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

//     @OneToMany(fetch = FetchType.LAZY)
//     @JoinColumn(name = "id_user")
//     private UserModel user;
    
    private UUID idUser;
}