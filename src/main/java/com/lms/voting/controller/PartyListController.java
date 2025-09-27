package com.lms.voting.controller;

import com.lms.voting.dto.PartyList;
import com.lms.voting.service.PartyListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/uk-party-list")
public class PartyListController {

    private final PartyListService partyListService;

    public PartyListController(PartyListService partyListService){
        this.partyListService = partyListService;
    }

    //get all party members
    @GetMapping
    public ResponseEntity<List<PartyList>> getAllPartyMembers(){
        List<PartyList> partyLists = partyListService.getAllPartyMembers();
        if (partyLists.isEmpty()){
            return new ResponseEntity<>(partyLists, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(partyLists, HttpStatus.OK);
    }
}
