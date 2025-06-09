package com.test.journal.controller;

import com.test.journal.entity.JournalEntry;
import com.test.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journalEntry")
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getEntries(){
        List<JournalEntry> allEntries = journalEntryService.getAllEntries();
        if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry){
        try {
            newEntry.setDate(LocalDateTime.now() );
            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<JournalEntry>(newEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{requestedId}")
    public ResponseEntity<JournalEntry> getEntryById (@PathVariable ObjectId requestedId){
        Optional<JournalEntry> entry = journalEntryService.getEntryById(requestedId);
        if(entry.isPresent()){
            return new ResponseEntity<JournalEntry>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{requestedId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId requestedId){
        journalEntryService.deleteById(requestedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("modifyEntry/{requestedId}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId requestedId, @RequestBody JournalEntry entry){
        JournalEntry oldEntry = journalEntryService.getEntryById(requestedId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : oldEntry.getTitle() );
            oldEntry.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<JournalEntry>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
