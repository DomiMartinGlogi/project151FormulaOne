package ch.wiss.project151formulaone;

import jakarta.persistence.*;

/**
 * The Driver class represents a driver entity in a race car system.
 */
@Entity
public class Driver {
    /**
     * The id variable represents the unique identifier of a Driver entity in a race car system.
     *
     * This variable is annotated with @Id, which indicates that it is the primary key of the entity.
     * It is also annotated with @GeneratedValue, which specifies the strategy used for generating the id value.
     * In this case, the GenerationType.IDENTITY strategy is used, which means that the database will generate
     * a unique id value for each new record automatically.
     *
     * Example usage:
     * Driver driver = new Driver();
     * Long id = driver.getId();
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name variable represents the name of a driver in a race car system.
     *
     * It is annotated with @Column, which indicates that it is mapped to a column in the database table. The "name" parameter
     * specifies the name of the column as "name". The "nullable" parameter is set to false, meaning that the name cannot be null.
     *
     * Example usage:
     * Driver driver = new Driver();
     * driver.setName("John");
     * String name = driver.getName();
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The country variable represents the country of a driver in a race car system.
     *
     * It is annotated with @Column, which indicates that it is mapped to a column in the database table. The "name" parameter
     * specifies the name of the column as "country". The "nullable" parameter is set to false, meaning that the country cannot be null.
     *
     * Example usage:
     * Driver driver = new Driver();
     * driver.setCountry("USA");
     * String country = driver.getCountry();
     *
     * @see Driver
     */
    @Column(name = "country", nullable = false)
    private String country;

    /**
     * The teamId variable represents the identifier of the team associated with a driver in a race car system.
     *
     * It is annotated with @Column, which indicates that it is mapped to a column in the database table. The "name" parameter
     * specifies the name of the column as "team_id".
     *
     * Example usage:
     * Driver driver = new Driver();
     * driver.setTeamId(12345L);
     * Long teamId = driver.getTeamId();
     *
     * @see Driver
     */
    @Column(name = "team_id")
    private Long teamId;

    /**
     * Constructs a new Driver object with the specified name, country, and team ID.
     *
     * @param name    the name of the driver
     * @param country the country of the driver
     * @param teamId  the team ID of the driver
     */
    public Driver(String name, String country, Long teamId) {
        this.name = name;
        this.country = country;
        this.teamId = teamId;
    }

    /**
     * Driver Default Constructor used internally.
     */
    public Driver() {

    }

    /**
     * Retrieves the ID of the driver.
     *
     * @return the ID of the driver
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the name of the driver.
     *
     * @return the name of the driver
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the driver.
     *
     * @param name the name of the driver
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the country of the driver.
     *
     * @return the country of the driver
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the driver.
     *
     * @param country the country of the driver
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the team ID of the driver.
     *
     * @return the team ID of the driver
     */
    public Long getTeamId() {
        return teamId;
    }

    /**
     * Sets the team ID of the driver.
     *
     * @param teamId the team ID of the driver
     */
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
