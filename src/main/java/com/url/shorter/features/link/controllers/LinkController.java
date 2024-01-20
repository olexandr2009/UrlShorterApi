package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.dto.LinkDto;
import com.url.shorter.features.link.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

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

