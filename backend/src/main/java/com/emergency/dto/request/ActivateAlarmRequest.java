package com.emergency.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateAlarmRequest {

    @NotBlank(message = "Activated by is required")
    private String activatedBy;

    @NotEmpty(message = "At least one notification method is required")
    private List<String> methods;
}
