package ch.wiss.project151formulaone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/driver")
public class DriverRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    final DriverRepository drivers;

    public DriverRestController(DriverRepository drivers) {
        this.drivers = drivers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<DriverDTO> maybeDriverDTO = drivers
                        .findById(id)
                        .map(DriverDTO::fromDomain);
        return ResponseEntity
                .of(maybeDriverDTO);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (drivers.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        drivers.deleteById(id);
        log.info("Deleted Resource {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
