package com.pedrovaledev.linktree.dto;

import jakarta.validation.constraints.NotBlank;

public record LinkRequestDto(
        @NotBlank(message = "Titulo obrigatório")
        String title,
        @NotBlank(message = "URL obrigatória")
        String url){}
