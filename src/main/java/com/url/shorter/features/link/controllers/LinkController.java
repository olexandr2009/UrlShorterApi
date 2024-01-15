package com.url.shorter.features.link.controllers;

import com.url.shorter.features.link.services.LinkService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @RequestMapping(value = "/{shortLink}", method = {RequestMethod.GET})
    public String findLinkByShortLink(@NotEmpty @RequestParam(value="shortLink") String shortLink) {
        return linkService.findByShortLink();
    }

    @DeleteMapping("/delete/{shortLink}")
    @RequestMapping(value = "/delete/{shortLink}" , method = {RequestMethod.DELETE})
    public ModelAndView deleteByShortLink(@NotEmpty @RequestParam(value="shortLink") String shortLink) {
        linkService.deleteByShortLink(shortLink);

        return linkList();
    }
}
