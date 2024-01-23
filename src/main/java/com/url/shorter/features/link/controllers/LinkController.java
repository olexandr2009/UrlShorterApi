package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Tag(name = "Links", description = "Links api")
@RequestMapping("/V1/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(
            summary = "Find by short link",
            tags = {"Links"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @GetMapping("/{shortLink}")
    public ResponseEntity<Optional<LinkDto>> findLinkByShortLink(@PathVariable String shortLink) {
        return ResponseEntity.ok(linkService.findByShortLink(shortLink));
    }

    @Operation(
            summary = "Delete",
            description = "delete Link by short link",
            tags = {"Links"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @DeleteMapping("/delete/{shortLink}")
    public ResponseEntity<Void> deleteByShortLink(@PathVariable String shortLink) {
        linkService.deleteByShortLink(shortLink);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Save",
            description = "save long link",
            tags = {"Links"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", description = "Long link isn't valid"),
            @ApiResponse(responseCode = "500"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @PostMapping("/save")
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

    @Operation(
            summary = "Find all links",
            tags = {"Links"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Can't get all links"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @GetMapping("/all/find")
    public ResponseEntity<List<LinkDto>> findAll() {
        try {
            List<LinkDto> allLinks = linkService.findAll();
            return new ResponseEntity<>(allLinks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Find all active links",
            tags = {"Links"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @GetMapping("/all/active")
    public ResponseEntity<List<LinkDto>> findActiveLinks() {
        List<LinkDto> activeLinks = linkService.findActiveLinks();
        return ResponseEntity.ok(activeLinks);
    }

    @Operation(
            summary = "Delete",
            description = "delete Link by long link",
            tags = {"Links"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @DeleteMapping("/delete/{longLink}")
    public ResponseEntity<Void> deleteByLongLink(@PathVariable String longLink) {
        linkService.deleteByLongLink(longLink);
        return ResponseEntity.ok().build();
    }
}