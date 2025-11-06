package com.emergency.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmergencyGroupRequest {

    private String type;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 100, message = "Number must not exceed 100 characters")
    private String number;

    private String notificationType;

    private String responsible;

    private LocalDate dateRevised;

    private String notes;

    private String emailTopic;

    private String emailContent;

    private String smsText;

    private Integer memberCount;

    private String createdBy;
}
