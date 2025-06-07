package com.test.journal.controller;

import com.test.journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journalEntry")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap();

    @GetMapping
    public List<JournalEntry> getEntries(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public String createEntry(@RequestBody JournalEntry newEntry){
        journalEntries.put(newEntry.getId(), newEntry);
        return newEntry.getTitle() + " added";
    }

    @GetMapping("{requestedId}")
    public JournalEntry getEntryById (@PathVariable Long requestedId){
        return journalEntries.get(requestedId);
    }

    @DeleteMapping("{requestedId}")
    public JournalEntry deleteEntry(@PathVariable Long requestedId){
        return journalEntries.remove(requestedId);
    }

    @PutMapping("modifyEntry/{requestedId}")
    public JournalEntry updateEntry(@PathVariable Long requestedId, @RequestBody JournalEntry entry){
        return journalEntries.put(requestedId, entry);
    }
}
