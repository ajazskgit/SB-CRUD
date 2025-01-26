package com.sama.journalApp.controller;

import com.sama.journalApp.entity.JournalEntry;
import com.sama.journalApp.repository.JournalEntryRepository;
import com.sama.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/hello")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getEntries();
    }

    @PostMapping
    public JournalEntry addEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
    journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getById(@PathVariable ObjectId myId){
        return journalEntryService.getById(myId).orElse(null);
    }

    @DeleteMapping("/id/{del}")
    public boolean deleteById(@PathVariable ObjectId del){
        journalEntryService.deleteById(del);
        return true;
    }
    
    @PutMapping("/id/{id}")
    public JournalEntry updateById(@PathVariable ObjectId id , @RequestBody JournalEntry myEntry){
        JournalEntry old = journalEntryService.getById(id).orElse(null);
        if (old !=null){
            old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("") ? myEntry.getContent(): old.getContent());
        }
        journalEntryService.saveEntry(old);
        return myEntry;
    }

}
