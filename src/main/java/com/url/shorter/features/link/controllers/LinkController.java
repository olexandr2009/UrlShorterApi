package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.entities.LinkEntity;
import com.url.shorter.features.link.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/{shortLink}")
    public ResponseEntity<LinkEntity> findLinkByShortLink(@PathVariable String shortLink) {
        String originalLink = String.valueOf(linkService.findByShortLink(shortLink));
        return (ResponseEntity<LinkEntity>) ResponseEntity.ok();
    }

    @DeleteMapping("/delete/{shortLink}")
    public ResponseEntity<Void> deleteByShortLink(@PathVariable String shortLink) {
        linkService.deleteByShortLink(shortLink);
        return ResponseEntity.ok().build();
    }
}
