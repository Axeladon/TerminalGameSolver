package com.example.TerminalV2.data.repository;

import com.example.TerminalV2.data.entity.TerminalWord;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class LocalRepository {
    private List<TerminalWord> terminalWordList;
    public LocalRepository(List<TerminalWord> terminalWordList) {
        this.terminalWordList = terminalWordList;
    }
    public void add(TerminalWord terminalWord) {
        terminalWordList.add(terminalWord);
    }
    public List<TerminalWord> getAll() {
        return terminalWordList;
    }

    public void delete(TerminalWord terminalWord) {
        terminalWordList.remove(terminalWord);
    }
    public void delete(String value) {
        terminalWordList.removeIf(terminalWord -> terminalWord.getWord().equals(value));
    }
    public void deleteAll() {
        terminalWordList.clear();
    }

    public TerminalWord getById(int i) {
        return terminalWordList.get(i);
    }
}
