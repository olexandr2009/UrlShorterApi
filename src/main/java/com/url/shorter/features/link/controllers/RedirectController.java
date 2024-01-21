package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class RedirectController {
    private final LinkServiceImpl linkService;

    public RedirectController(LinkServiceImpl linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{shortUrl}")
    public String redirectToLongUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {

        LinkDto linkDto = linkService.redirect(shortUrl);
        String longUrl = linkDto.getOriginUrl();

        response.sendRedirect(longUrl);

        return null;
    }
}
