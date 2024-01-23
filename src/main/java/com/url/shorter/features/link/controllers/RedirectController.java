package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RedirectController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/{shortLink}")
    public void redirectToLongLink(@PathVariable String shortLink, HttpServletResponse response) throws IOException {
        try {
            LinkDto linkDto = linkService.findByShortLink(shortLink).orElseThrow();
            linkService.incrementUseCount(linkDto);
            response.sendRedirect(linkDto.getOriginUrl());
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
