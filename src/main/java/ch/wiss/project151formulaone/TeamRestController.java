package ch.wiss.project151formulaone;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * The TeamRestController class is a REST controller that handles HTTP requests related to teams.
 * It provides methods for retrieving, creating, updating, and deleting team entities.
 */
@RestController
@RequestMapping("api/team")
public class TeamRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    final TeamRepository teams;

    /**
     * The TeamRestController class is a REST controller that handles HTTP requests related to teams.
     * It provides methods for retrieving, creating, updating, and deleting team entities.
     */
    public TeamRestController(TeamRepository teams) {
        this.teams = teams;
    }

    /**
     * Retrieves all teams.
     *
     * This method retrieves all teams from the database and converts them to a list of TeamDTO objects using the fromDomain method.
     * The list of TeamDTO objects is then returned wrapped in a ResponseEntity with an HTTP OK status.
     *
     * @return A ResponseEntity containing a list of TeamDTO objects representing all teams.
     */
    @GetMapping("/all")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        List<TeamDTO> teamDTOs = teams.findAll().stream()
                .map(TeamDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOs);
    }

    /**
     * Retrieves a team by ID.
     *
     * This method retrieves a team from the database based on the provided ID. If a team with the specified ID exists,
     * a {@code TeamDTO} representing the team is returned. Otherwise, an empty {@code ResponseEntity} is returned.
     *
     * @param id The ID of the team to retrieve.
     * @return A {@code ResponseEntity} containing a {@code TeamDTO} if the team is found, or an empty {@code ResponseEntity} if the team is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<TeamDTO> maybeTeamDTO = teams
                .findById(id)
                .map(TeamDTO::fromDomain);
        return ResponseEntity
                .of(maybeTeamDTO);
    }

    /**
     * Creates a new team.
     *
     * This method is used to create a new team by providing a teamDTO object. The teamDTO object contains the necessary
     * information such as name and country of the team. The teamID must be null in order to create a new team. If the
     * teamID is not null, a BAD_REQUEST status with an error message is returned.
     *
     * After creating the new team, it is saved in the team repository. The newly created team is then returned with a
     * CREATED status and the URI for the team endpoint. The teamDTO object is converted to a domain object before returning.
     *
     * @param teamDTO The teamDTO object containing the information for the new team.
     * @return A ResponseEntity with the newly created team and the URI for the team endpoint.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TeamDTO teamDTO) {
        if (teamDTO.id() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Team ID must be null");
        }

        Team newTeam = new Team();
        newTeam.setName(teamDTO.name());
        newTeam.setCountry(teamDTO.country());

        teams.save(newTeam);

        return ResponseEntity
                .created(URI.create("/api/team/" + newTeam.getId()))
                .body(TeamDTO.fromDomain(newTeam));
    }

    /**
     * Delete a team by its ID.
     *
     * This method deletes a team with the specified ID. If the team is found and deleted successfully,
     * it returns an HTTP OK status. If the team is not found, it returns an HTTP NOT_FOUND status.
     *
     * @param id The ID of the team to be deleted.
     * @return An HTTP response with the corresponding status (OK or NOT_FOUND).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (teams.existsById(id)) {
            teams.deleteById(id);
            log.info("Deleted Resource {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Updates a team with the given ID.
     *
     * @param id      The ID of the team to update.
     * @param teamDTO The updated information for the team.
     * @return ResponseEntity with the updated team if it exists, or an error message if the team does not exist or if the ID in the path does not match the ID in the DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody TeamDTO teamDTO) {
        if (teamDTO.id() != null && teamDTO.id() != id) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Path Variable of id not equal to Team ID");
        }

        Optional<Team> maybeTeam = teams.findById(id);
        if (maybeTeam.isPresent()) {
            Team team = maybeTeam.get();
            team.setName(teamDTO.name());
            team.setCountry(teamDTO.country());
            teams.save(team);
            return ResponseEntity
                    .ok(TeamDTO.fromDomain(team));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Team of ID " + id + " not found");
        }
    }
}