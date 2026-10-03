package com.pedrovaledev.linktree.service;

import com.pedrovaledev.linktree.dto.LinkRequestDto;
import com.pedrovaledev.linktree.dto.LinkResponseDto;
import com.pedrovaledev.linktree.exception.LinkNotFoundException;
import com.pedrovaledev.linktree.model.Link;
import com.pedrovaledev.linktree.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public List<LinkResponseDto> findAll() {
        List<Link> links = linkRepository.findAll();

        if (links.isEmpty()) {
            throw new LinkNotFoundException("404 - Links inexistentes");
        }
            return links.stream().map(LinkResponseDto::fromEntity).toList();
    };

    public LinkResponseDto findById(Long id) {

        Link link = linkRepository.findById(id).orElseThrow(()-> new LinkNotFoundException("404 - Id do link inexistente"));

            return new LinkResponseDto(link.getId(), link.getTitle(), link.getUrl(), link.getClickCount());
    }

    public LinkResponseDto createLink(LinkRequestDto linkRequestDto) {
        Link newLink = new Link(linkRequestDto.title(), linkRequestDto.url());
        Link savedLink = linkRepository.save(newLink);

        return LinkResponseDto.fromEntity(savedLink);
    }

    public LinkResponseDto registerClick(Long id){
         Link link = linkRepository.findById(id).orElseThrow(()-> new RuntimeException("Id do link inexistente"));

         link.incrementClickCount();
         Link savedLink = linkRepository.save(link);

         return LinkResponseDto.fromEntity(savedLink);

    }

    public LinkResponseDto updateLink(Long id, LinkRequestDto linkRequestDto) {
        Link linkFound = linkRepository.findById(id).orElseThrow(()-> new RuntimeException("Id do link inexistente"));
        linkFound.setTitle(linkRequestDto.title());
        linkFound.setUrl(linkRequestDto.url());

        Link savedLink = linkRepository.save(linkFound);

        return LinkResponseDto.fromEntity(savedLink);

    }

    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }
}
