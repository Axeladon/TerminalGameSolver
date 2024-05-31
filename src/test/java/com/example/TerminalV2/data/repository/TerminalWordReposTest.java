package com.example.TerminalV2.data.repository;

import com.example.TerminalV2.data.entity.TerminalWord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TerminalWordReposTest {
    @Autowired
    LocalRepository terminalWordRepos;

    @BeforeEach
    void setUp() {
        terminalWordRepos.add(new TerminalWord("City"));
        terminalWordRepos.add(new TerminalWord("Home"));
        terminalWordRepos.add(new TerminalWord("Work"));
    }

    @Test
    void addTest() {
        terminalWordRepos.add(new TerminalWord("Test"));
        assertEquals(4, terminalWordRepos.getAll().size());
        assertEquals("Test", terminalWordRepos.getById(3).getWord());
    }

    @Test
    void deleteTest() {
        TerminalWord terminalWord = terminalWordRepos.getById(0);
        terminalWordRepos.delete(terminalWord);
        assertEquals(2, terminalWordRepos.getAll().size());
    }

    @AfterEach
    void destruction() {
        terminalWordRepos.getAll().clear();
    }

}
