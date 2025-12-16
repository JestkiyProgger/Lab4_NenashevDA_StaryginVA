package com.example.Lab4_NenashevDA_StaryginVA.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tasks")
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title Not Blank")
    @Size(min = 1, max = 255, message = "1 <= Size <= 255")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @XmlTransient
    private User user;

    @NotNull(message = "Created date Not Null")
    @Column(name = "created_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @NotNull(message = "Completed status Not Null")
    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    public Task() {
        this.createdDate = LocalDate.now();
    }

    public Task(String title, User user, LocalDate createdDate, Boolean completed) {
        this.title = title;
        this.user = user;
        this.createdDate = createdDate != null ? createdDate : LocalDate.now();
        this.completed = completed != null ? completed : false;
    }

    @XmlElement(name = "userId")
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                ", createdDate=" + createdDate +
                ", completed=" + completed +
                '}';
    }
}
