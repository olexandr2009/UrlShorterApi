package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{shortLink}")
    public void redirectToLongLink(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
        LinkDto linkDto = linkService.redirect(shortLink);
        if (linkDto != null) {
            response.sendRedirect(linkDto.getLongLink());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short link not found");
        }
    }
}
