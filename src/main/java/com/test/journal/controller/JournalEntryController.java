package com.test.journal.controller;

import com.test.journal.entity.JournalEntry;
import com.test.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journalEntry")
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getEntries(){
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public String createEntry(@RequestBody JournalEntry newEntry){
        newEntry.setDate(LocalDateTime.now() );
        journalEntryService.saveEntry(newEntry);
        return "Entry Added";
    }

    @GetMapping("{requestedId}")
    public JournalEntry getEntryById (@PathVariable ObjectId requestedId){
        return journalEntryService.getEntryById(requestedId).orElse(null);
    }

    @DeleteMapping("{requestedId}")
    public boolean deleteEntry(@PathVariable ObjectId requestedId){
        return journalEntryService.deleteById(requestedId);
    }

    @PutMapping("modifyEntry/{requestedId}")
    public JournalEntry updateEntry(@PathVariable ObjectId requestedId, @RequestBody JournalEntry entry){
        return journalEntryService.updateEntry(requestedId, entry);
    }
}
