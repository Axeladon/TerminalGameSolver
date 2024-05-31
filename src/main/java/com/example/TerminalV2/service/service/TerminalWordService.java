package com.example.TerminalV2.service.service;

import com.example.TerminalV2.data.entity.TerminalWord;
import com.example.TerminalV2.data.repository.LocalRepository;
import com.example.TerminalV2.service.util.TextTransformer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TerminalWordService {
    private final LocalRepository localRepository;
    TerminalWordService(LocalRepository terminalWordRepos) {
        this.localRepository = terminalWordRepos;
    }
    public void processAndSaveWords(String json) {
        localRepository.deleteAll();

        List<String> wordsList = TextTransformer.getWordsFromJson(json);
        for (String wordList : wordsList) {
            localRepository.add(new TerminalWord(wordList));
        }
    }
    public Map<String, String> getMapOfRelevantWords() {
        Map<String, String> map = new HashMap<>();
        List<String> words = getAllRelevantWords();
        StringBuilder buildWord = new StringBuilder();

        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                int numberOfMatches = 1;
                for (int j = 0; j < words.size(); j++) {
                    if (!word.equals(words.get(j))) {
                        if (word.charAt(i) == words.get(j).charAt(i)) {
                            numberOfMatches++;
                        }
                    }
                }
                buildWord.append(numberOfMatches + " ");
            }
            map.put(word, buildWord.toString());
            buildWord.setLength(0); //to free memory of builder
        }
        return map;
    }

    public void deleteIrrelevantWords(String mainWord, int matchesCount) {
        localRepository.delete(mainWord); //delete chosen word to avoid checking of it
        prepareWordToDelete(mainWord, matchesCount).stream()
                .forEach(word -> localRepository.delete(word));
    }

    private List<TerminalWord> prepareWordToDelete(String mainWord, int matchesCount) {
        List<TerminalWord> wordToDelete = new ArrayList<>();
        for (TerminalWord terminalWord : localRepository.getAll()) {
            int matchesCounter = 0;
            for (int i = 0; i < mainWord.length(); i++) {
                if (mainWord.charAt(i) == terminalWord.getWord().charAt(i)){
                    matchesCounter++;
                }
            }
            if (matchesCounter != matchesCount) {
                wordToDelete.add(terminalWord);
            }
        }
        return wordToDelete;
    }
    
    public List<String> getAllRelevantWords() {
        return localRepository.getAll().stream()
                .map(TerminalWord::getWord)
                .collect(Collectors.toList());
    }
}
