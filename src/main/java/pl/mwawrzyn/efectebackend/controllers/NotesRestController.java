package pl.mwawrzyn.efectebackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotesRestController {

    @GetMapping("")
    public String getDemoNote() {
        return "aaa";
    }
}
