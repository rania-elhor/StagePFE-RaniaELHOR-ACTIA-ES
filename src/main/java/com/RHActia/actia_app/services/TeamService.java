package com.RHActia.actia_app.services;

import com.RHActia.actia_app.model.Team;
import com.RHActia.actia_app.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team addTeam(Team team) {
        // Check if the team with the same name already exists
        Team existingTeam = teamRepository.findByTeamName(team.getTeam_name());
        if (existingTeam != null) {
            // Team with the same name already exists, you may handle this case according to your requirements
            // For now, I'm throwing an exception, you can handle it differently based on your needs
            throw new IllegalArgumentException("Team with the same name already exists");
        }
        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(int id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Team getTeamByName(String name) {
        return teamRepository.findByTeamName(name);
    }

    public Team updateTeam(Team team) {
        return teamRepository.save(team);
    }

    public boolean deleteTeamById(int id) {
        teamRepository.deleteById(id);
        return true;
    }
}
