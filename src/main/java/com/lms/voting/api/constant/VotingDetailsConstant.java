package com.lms.voting.api.constant;


public class VotingDetailsConstant {
    private VotingDetailsConstant() {

    }

    public static final String RESULT_VOTED_SUCCESS = "The user has been successfully voted {}";
    public static final String TOTAL_COUNT_RETRIEVED_SUCCESS = "Total votes {}";
    public static final String PARTY_TOTAL_VOTE_SUCCESS = "Total vote for party {} is {}";
    public static final String ERR_MAX_VALID_ADDRESSES_REACHED = "User has reached the maximum number of valid addresses";
    public static final int MINIMUM_VOTING_AGE = 18;
    public static final String USER_HAS_REACHED_THE_MAXIMUM_NUMBER_OF_POLL_REFERENCES = "User has reached the maximum number of poll references";

    public static final String ERR_MAX_NUM_ON_REGISTER_REACHED = "User has reached the maximum number of number on register reached";

    public static final int MAX_POLL_REFERENCES_PER_USER = 2;

}
