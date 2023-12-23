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
 * Rest controller for managing driver resources.
 */
@RestController
@RequestMapping("api/driver")
public class DriverRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    final DriverRepository drivers;

    public DriverRestController(DriverRepository drivers) {
        this.drivers = drivers;
    }

    /**
     * Retrieves all teams in the race car system.
     *
     * @return the ResponseEntity containing a list of DriverDTO objects representing the teams
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllTeams() {
        List<DriverDTO> driverDTOs = drivers.findAll()
                .stream()
                .map(DriverDTO::fromDomain)
                .collect(Collectors.toList());
        System.out.println(driverDTOs.size());
        return ResponseEntity.ok(driverDTOs);
    }

    /**
     * Retrieves a driver by ID.
     *
     * @param id the ID of the driver to retrieve
     * @return the ResponseEntity containing the driver data, or empty if the driver is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<DriverDTO> maybeDriverDTO = drivers
                        .findById(id)
                        .map(DriverDTO::fromDomain);
        return ResponseEntity
                .of(maybeDriverDTO);
    }

    /**
     * Creates a new driver by taking in a DriverDTO object.
     * If the driverDTO.id is not null, returns a ResponseEntity with BAD_REQUEST status and error message "Driver ID must be null".
     * Otherwise, creates a new Driver object using the driverDTO.name, driverDTO.country, and driverDTO.teamId values.
     * Returns a ResponseEntity with CREATED status, location URI "/api/driver/{driverId}",
     * and the DriverDTO object created from the newDriver object.
     *
     * @param driverDTO the DriverDTO object containing the driver details
     * @return a ResponseEntity with CREATED status and the created DriverDTO object
     * @see DriverDTO
     * @see ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DriverDTO driverDTO) {
        if (driverDTO.id() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Driver ID must be null");
        }

        Driver newDriver = new Driver(driverDTO.name(), driverDTO.country(), driverDTO.teamId());

        return ResponseEntity
                .created(URI.create("/api/driver/" + newDriver.getId()))
                .body(DriverDTO.fromDomain(newDriver));
    }

    /**
     * Deletes a driver by ID.
     *
     * @param id the ID of the driver to delete
     * @return a ResponseEntity representing the status of the deletion operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (!drivers.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        drivers.deleteById(id);
        log.info("Deleted Resource {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates a driver with the given ID using the provided DriverDTO object.
     * If the driverDTO.id is not null and not equal to the path variable id,
     * returns a ResponseEntity with I_AM_A_TEAPOT status and the error message "Path Variable of id not equal to Driver ID".
     * If the driver with the given ID exists, updates the driver's name, country, and teamId properties with the values from the driverDTO object.
     * Saves the updated driver in the repository and returns a ResponseEntity with OK status and the updated DriverDTO object.
     * If the driver with the given ID does not exist, returns a ResponseEntity with NOT_FOUND status and the error message "Driver of ID {id} not found".
     *
     * @param id         the ID of the driver to update
     * @param driverDTO  the DriverDTO object containing the updated driver details
     * @return a ResponseEntity with the updated DriverDTO object or an error message
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody DriverDTO driverDTO) {
        if (driverDTO.id() != null && driverDTO.id() != id) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Path Variable of id not equal to Driver ID");
        }

        Optional<Driver> maybeDriver = drivers.findById(id);
        if (maybeDriver.isPresent()) {
            Driver driver = maybeDriver.get();
            driver.setName(driverDTO.name());
            driver.setCountry(driverDTO.country());
            driver.setTeamId(driverDTO.teamId());
            drivers.save(driver);
            return ResponseEntity
                    .ok(DriverDTO.fromDomain(driver));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Driver of ID " + id + " not found");
        }
    }
}
