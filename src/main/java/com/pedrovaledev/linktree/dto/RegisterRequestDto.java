package com.pedrovaledev.linktree.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(@NotBlank String userName, @NotBlank String password) {}
