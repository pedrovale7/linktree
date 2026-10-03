package com.pedrovaledev.linktree.dto;

import com.pedrovaledev.linktree.model.Link;

public record LinkResponseDto(Long id, String title, String url, Integer clickCount) {

    public static LinkResponseDto fromEntity(Link link) {
        return new LinkResponseDto(
                link.getId(),
                link.getTitle(),
                link.getUrl(),
                link.getClickCount()
        );
    }
}
