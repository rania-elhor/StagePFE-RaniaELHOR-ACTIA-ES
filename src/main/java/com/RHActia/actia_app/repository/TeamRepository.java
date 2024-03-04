package com.RHActia.actia_app.repository;

import com.RHActia.actia_app.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("SELECT t FROM Team t WHERE t.team_name = ?1")
    Team findByTeamName(String teamName);
}