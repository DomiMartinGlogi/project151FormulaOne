package ch.wiss.project151formulaone;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The DriverDTO class represents a data transfer object for Driver entities.
 * It contains the ID, name, country, and team ID of a driver.
 */
public record DriverDTO(Long id, String name, String country, Long teamId) {
    @Contract("_ -> new")
    public static @NotNull DriverDTO fromDomain(Driver driver) {
        return new DriverDTO(driver.getId(), driver.getName(), driver.getCountry(), driver.getTeamId());
    }
}
