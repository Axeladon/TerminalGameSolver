package com.example.TerminalV2.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextTransformer {
    public static List<String> getWordsFromJson(String jsonText) {
        List<String> wordlist = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonText);

            JsonNode wordsNode = jsonNode.get("words");
            for (JsonNode wordNode : wordsNode) {
                wordlist.add(wordNode.asText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordlist;
    }
}
