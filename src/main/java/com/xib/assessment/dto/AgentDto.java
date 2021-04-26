package com.xib.assessment.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xib.assessment.model.Team;

@JsonSerialize
public class AgentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Team team;


}
