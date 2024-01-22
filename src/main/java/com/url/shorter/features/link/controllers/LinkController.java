package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import com.url.shorter.features.link.services.ShortLinkGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/V1/api/links")
public class LinkController {

    private final LinkService linkService;
    private final ShortLinkGenerator shortLinkGenerator;

    public LinkController(LinkService linkService, ShortLinkGenerator shortLinkGenerator) {
        this.linkService = linkService;
        this.shortLinkGenerator = shortLinkGenerator;
    }

    @PostMapping("/saveLongLink")
    public ResponseEntity<LinkDto> saveLongLink(
            @RequestParam String longLink,
            @RequestParam(required = false) LocalDateTime dateExp,
            @RequestParam(required = false) String userId
    ) {
        try {
            LinkDto linkDto = LinkDto.builder()
                    .originUrl(longLink)
                    .shortUrl(null)
                    .creationDate(LocalDateTime.now())
                    .expirationDate(dateExp)
                    .openCount(0)
                    .userId(UUID.fromString(userId))
                    .build();

            LinkDto savedLink = linkService.createByLongLink(linkDto);

            return new ResponseEntity<>(savedLink, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<LinkDto>> findAll() {
        try {
            List<LinkDto> allLinks = linkService.findAll();
            return new ResponseEntity<>(allLinks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<LinkDto>> findActiveLinks() {
        List<LinkDto> activeLinks = linkService.findActiveLinks();
        return ResponseEntity.ok(activeLinks);
    }

    @DeleteMapping("/delete/{longLink}")
    public ResponseEntity<Void> deleteByLongLink(@PathVariable String longLink) {
        linkService.deleteByLongLink(longLink);
        return ResponseEntity.ok().build();
    }
}