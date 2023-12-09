package ch.wiss.project151formulaone;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record TeamDTO(Long id, String name, String country) {

    @Contract("_ -> new")
    public static @NotNull TeamDTO fromDomain(Team team) {
        return new TeamDTO(team.getId(), team.getName(), team.getCountry());
    }
}
