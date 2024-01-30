package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Links", description = "Links api")
@RequestMapping("/V1/links")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @Operation(
            summary = "Find by long link",
            tags = {"Links"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(
                    schema = @Schema(
                            implementation = LinkDto.class
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Link not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @GetMapping("/find")
    public ResponseEntity<?> findLinkByLongLink(@RequestParam String longLink) {
            Optional<LinkDto> optionalLink = linkService.findByLongLink(longLink);
            if (optionalLink.isPresent()){
                return ResponseEntity.ok(optionalLink);
            }
            return ResponseEntity.badRequest().body("Link not found");
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
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Long link isn't valid or it already exists"),
            @ApiResponse(responseCode = "403", description = "Unauthorized authorize in Authentication login")
    })
    @PostMapping("/save")
    public ResponseEntity<?> saveLongLink(
            @RequestParam String longLink,
            @RequestParam(required = false) LocalDateTime dateExp,
            Principal principal
    ) {
        try {
            LinkDto linkDto = LinkDto.builder()
                    .longLink(longLink)
                    .creationDate(LocalDateTime.now())
                    .expirationDate(dateExp)
                    .openCount(0)
                    .ownerName(principal.getName())
                    .build();

            LinkDto savedLink = linkService.createByLongLink(linkDto);

            return ResponseEntity.ok().body(savedLink);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Collection.class)
                    )
            ),
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
