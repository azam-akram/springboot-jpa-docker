package com.azamakram.github.springbootjpadocker.model.output;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageOutput {

    @NotNull
    private String messageKey;

    @NotBlank
    private String sender;

    @NotNull
    private LocalDateTime savedAt;
}