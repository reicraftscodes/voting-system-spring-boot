package com.lms.voting.controller;

import com.lms.voting.entity.PartyList;
import com.lms.voting.service.PartyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/uk-party-list")
public class PartyListController {

    private final PartyListService partyListService;

    @Autowired
    public PartyListController(PartyListService partyListService) {
        this.partyListService = partyListService;
    }

    //get all party members
    @GetMapping
    public ResponseEntity<List<PartyList>> getAllPartyMembers() {
        List<PartyList> partyLists = partyListService.getAllPartyMembers();
        return new ResponseEntity<>(partyLists, HttpStatus.OK);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<PartyList> createPartyList(@RequestBody PartyList partyList) {
        PartyList addPartyList = partyListService.createPartyList(partyList);
        return new ResponseEntity<>(addPartyList, HttpStatus.OK);
    }

}
