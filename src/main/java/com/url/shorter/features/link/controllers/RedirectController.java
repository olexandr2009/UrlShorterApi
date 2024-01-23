package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Tag(name = "Redirect", description = "Redirector to long Url")
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }
    @Operation(
            summary = "Redirect",
            description = "redirect all users by url",
            tags = {"Redirect"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302"),
            @ApiResponse(responseCode = "404", description = "Short link not found")
    })
    @GetMapping("/{shortLink}")
    public void redirectToLongLink(
            @Parameter(required = true, description = "short url")
            @PathVariable String shortLink, HttpServletResponse response) throws IOException {
        LinkDto linkDto = linkService.redirect(shortLink);
        if (linkDto != null) {
            response.sendRedirect(linkDto.getLongLink());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short link not found");
        }
    }
}
