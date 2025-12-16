package com.example.Lab4_NenashevDA_StaryginVA.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "taskRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskRequest {

    @NotBlank(message = "Title Not Blank")
    private String title;

    @NotNull(message = "User ID Not Null")
    private Long userId;

    private Boolean completed = false;
}
