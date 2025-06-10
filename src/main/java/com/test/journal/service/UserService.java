package com.test.journal.service;

import com.test.journal.entity.JournalEntry;
import com.test.journal.entity.User;
import com.test.journal.repository.JournalEntryRepository;
import com.test.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public List<User> getAllEntries(){
        return userRepository.findAll();
    }

    public Optional<User> getEntryById(ObjectId id){
        return userRepository.findById(id);
    }

    public boolean deleteById(ObjectId id){
        userRepository.deleteById(id);
        return true;
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
