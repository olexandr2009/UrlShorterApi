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
@RequestMapping("/api/links")
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
            @RequestParam(required = false) String shortLink,
            @RequestParam(required = false) LocalDateTime dateCreate,
            @RequestParam(required = false) LocalDateTime dateExp,
            @RequestParam(required = false) Integer clicks,
            @RequestParam(required = false) String user
    ) {
        try {
            if (shortLink == null || shortLink.isEmpty()) {
                shortLink = shortLinkGenerator.shortLinkGenerator(longLink);
            }
            LinkDto linkDto = LinkDto.builder()
                    .originUrl(longLink)
                    .shortUrl(shortLink)
                    .creationDate(dateCreate)
                    .expirationDate(dateExp)
                    .openCount(clicks)
                    .userId(UUID.fromString(user))
                    .build();

            LinkDto savedLink = linkService.createByLongLink(null);

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
}
