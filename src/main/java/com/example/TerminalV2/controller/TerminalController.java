package com.example.TerminalV2.controller;

import com.example.TerminalV2.service.service.TerminalWordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class TerminalController {
    private final TerminalWordService terminalWordService;

    TerminalController(TerminalWordService terminalWordService) {
        this.terminalWordService = terminalWordService;
    }

    @GetMapping("terminal/start")
    public String getMainPage() {
        return "main";
    }

    @PostMapping("terminal/submit-words")
    public ModelAndView submitWords(@RequestParam("text") String json){
        ModelAndView modelAndView = new ModelAndView("buttonsRendering");
        terminalWordService.processAndSaveWords(json);
        modelAndView.addObject("map", terminalWordService.getMapOfRelevantWords());
        return modelAndView;
    }

    @PostMapping("terminal/enter-a-number")
    public ModelAndView modalWindow(@RequestParam("selectedWord") String selectedWord){
        ModelAndView modelAndView = new ModelAndView("modalWindow");
        modelAndView.addObject("selectedWord", selectedWord);
        modelAndView.addObject("selectedWordLength", selectedWord.length());
        return modelAndView;
    }

    @PostMapping("terminal/submit-number")
    public ModelAndView verifyWords(@RequestParam("selectedWord") String selectedWord,
                                    @RequestParam("number") int number){
        ModelAndView modelAndView = new ModelAndView("buttonsRendering");
        terminalWordService.deleteIrrelevantWords(selectedWord, number);
        modelAndView.addObject("map", terminalWordService.getMapOfRelevantWords());
        return modelAndView;
    }
}
