package com.RHActia.actia_app.controller;

import com.RHActia.actia_app.model.Team;
import com.RHActia.actia_app.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // Add new team
    @PostMapping("/addTeam")
    public Team addTeam(@RequestBody Team team) {
        return teamService.addTeam(team);
    }

    // Get all teams
    @GetMapping("/getAllTeams")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    // Get team by ID
    @GetMapping("/getTeamById/{id}")
    public Team getTeamById(@PathVariable int id) {
        return teamService.getTeamById(id);
    }
    // get team by name
    @GetMapping("/getTeamByName/{name}")
    public Team getTeamByName(@PathVariable String name) {
        return teamService.getTeamByName(name);
    }

    // Update team
    @PutMapping("/updateTeam")
    public Team updateTeam(@RequestBody Team team) {
        return teamService.updateTeam(team);
    }

    // Delete team by ID
    @DeleteMapping("/deleteTeamById/{id}")
    public boolean deleteTeamById(@PathVariable int id) {
        return teamService.deleteTeamById(id);
    }
}
