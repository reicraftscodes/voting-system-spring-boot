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

    private final PartyListService partyListServiceImp;

    @Autowired
    public PartyListController(PartyListService partyListService){
        this.partyListServiceImp = partyListService;
    }

    //get all party members
    @GetMapping
    public ResponseEntity<List<PartyList>> getAllPartyMembers() {
        List<PartyList> partyLists = partyListServiceImp.getAllPartyMembers();
        if (partyLists.isEmpty()) {
            return new ResponseEntity<>(partyLists, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(partyLists, HttpStatus.OK);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<PartyList> createPartyList(@RequestBody PartyList partyList) {
        PartyList addPartyList = partyListServiceImp.createPartyList(partyList);
        if (addPartyList == null) {
            return new ResponseEntity<>(addPartyList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(addPartyList, HttpStatus.OK);
    }

}
