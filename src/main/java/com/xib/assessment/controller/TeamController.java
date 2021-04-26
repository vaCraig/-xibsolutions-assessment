package com.xib.assessment.controller;

import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import com.xib.assessment.repository.AgentRepository;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController("/team")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @GetMapping("/getTeams")
    public ResponseEntity<?> getTeams() {

        List<Team> teams;

        try {
            teams = teamRepository.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/getTeam/{id}")
    public ResponseEntity<?> findTeam(@PathVariable("id") Long id) {

        Team team = null;

        try {
            team = teamRepository.findById(id).orElse(null);

            if (Objects.isNull(team)) return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(team);
    }

    @PostMapping("/createTeam")
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        try {
            team = teamRepository.save(team);
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateTeamAgent/{teamId}/agent/{agentId}")
    public ResponseEntity<?> updateTeamAgent(@PathVariable("teamId") long teamId, @PathVariable("agentId") long agentId) {
        try {
            Team team = teamRepository.findById(teamId).orElse(null);

            if (Objects.nonNull(team)) {

                Agent agent = agentRepository.findById(agentId).orElse(null);

                if (Objects.nonNull(agent)) {

                    team.setAgent(agent);

                    teamRepository.save(team);

                } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateTeamManager/{teamId}/manager/{managerId}")
    public ResponseEntity<?> updateTeamManager(@PathVariable("teamId") long teamId, @PathVariable("managerId") long agentId) {
        try {
            Team team = teamRepository.findById(teamId).orElse(null);

            if (Objects.nonNull(team)) {

                Manager manager = managerRepository.findById(agentId).orElse(null);

                if (Objects.nonNull(manager)) {

                    team.setManager(manager);

                    teamRepository.save(team);

                } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFreeTeams")
    public ResponseEntity<?> getFreeTeams() {

        List<Team> teams;

        try {
            teams = teamRepository.findByAgentAndManager(null, null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(teams);
    }
}