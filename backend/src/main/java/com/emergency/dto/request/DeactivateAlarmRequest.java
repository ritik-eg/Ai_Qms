package com.emergency.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateAlarmRequest {

    @NotBlank(message = "Deactivated by is required")
    private String deactivatedBy;

    private String notes;
}
