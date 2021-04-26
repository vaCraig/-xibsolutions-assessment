package com.xib.assessment.controller;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.model.Agent;
import com.xib.assessment.repository.AgentRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController("agent")
public class AgentController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AgentRepository agentRepository;


    @PostMapping("/createAgent")
    public ResponseEntity<?> createAgent(@RequestBody Agent agent) {
        try {
            agent = agentRepository.save(agent);
            return ResponseEntity.ok(agent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAgent/{id}")
    public ResponseEntity<?> getAgent(@PathVariable("id") Long id) {
        Agent agent = null;

        try {
            agent = agentRepository.findById(id).orElse(null);

            if (Objects.isNull(agent)) return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(agent);
    }

    @GetMapping("/getAgents")
    public ResponseEntity<?> getAgents() {

        List<Agent> agents;

        try {
            agents = agentRepository.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/getAgents/page/{page}/size/{size}")
    public ResponseEntity<?> getAgentsPaginated(@PathVariable("page") int page, @PathVariable("size") int size) {

        List<AgentDto> agents = new ArrayList<>();

        try {
            PageRequest pageRequest = PageRequest.of(page, size);

            Page<Agent> agentPage = agentRepository.findAll(pageRequest);

            if (!agentPage.isEmpty()) {
                agents = agentPage.getContent().stream().map(agent -> {
                    AgentDto agentDto = new AgentDto();
                    BeanUtils.copyProperties(agent, agentDto);
                    return agentDto;
                }).collect(Collectors.toList());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(agents);
    }
}