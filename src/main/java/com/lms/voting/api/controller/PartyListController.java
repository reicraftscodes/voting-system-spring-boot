package com.lms.voting.api.controller;

import com.lms.voting.api.constant.PartyListConstant;
import com.lms.voting.api.model.dto.PartyListDto;
import com.lms.voting.api.service.PartyListService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/uk/parties")
public class PartyListController {

    private final PartyListService partyListService;

    @Autowired
    public PartyListController(PartyListService partyListService) {
        this.partyListService = partyListService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<PartyListDto> createPartyList(@RequestBody @Valid PartyListDto partyListDto) {
        PartyListDto addPartyList = partyListService.createPartyList(partyListDto);
        log.info(PartyListConstant.RESULT_DESCRIPTION_SUCCESS, addPartyList.getPartyName());
        return ResponseEntity.status(HttpStatus.OK).body(addPartyList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllPartyMembers() {
        List<String> partyLists = partyListService.getAllPartyMembers();
        log.info(PartyListConstant.RETRIEVE_DESCRIPTION_SUCCESS, partyLists);
        return ResponseEntity.status(HttpStatus.OK).body(partyLists);
    }

}
