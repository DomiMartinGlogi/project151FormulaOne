package ch.wiss.project151formulaone;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record DriverDTO(Long id, String name, String country, Long teamId) {
    @Contract("_ -> new")
    public static @NotNull DriverDTO fromDomain(Driver driver) {
        return new DriverDTO(driver.getId(), driver.getName(), driver.getCountry(), driver.getTeamId());
    }
}
