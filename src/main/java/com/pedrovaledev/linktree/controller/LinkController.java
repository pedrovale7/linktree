package com.pedrovaledev.linktree.controller;

import com.pedrovaledev.linktree.dto.LinkRequestDto;
import com.pedrovaledev.linktree.dto.LinkResponseDto;
import com.pedrovaledev.linktree.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/links")
@CrossOrigin(origins = "http://localhost:5173")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping
    public List<LinkResponseDto> findAll() {
        return linkService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<LinkResponseDto> findLinkById(@PathVariable Long id) {
        return ResponseEntity.ok(linkService.findById(id));
    }

    @GetMapping("/{id}/go")
    public ResponseEntity<LinkResponseDto> goToLink(@PathVariable Long id) {

        LinkResponseDto linkClicked = linkService.registerClick(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(linkClicked.url()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<LinkResponseDto> create(@RequestBody LinkRequestDto linkRequestDto) {
        LinkResponseDto linkCreated = linkService.createLink(linkRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(linkCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkResponseDto> update(@PathVariable Long id, @RequestBody LinkRequestDto linkRequestDto) {
        LinkResponseDto linkUpdated = linkService.updateLink(id, linkRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(linkUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LinkResponseDto> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
