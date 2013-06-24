package models;

import play.db.jpa.JPA;
import javax.persistence.*;
import play.data.validation.Constraints;

/**
 * Level entity managed by JPA
 */
@Entity 
public class Level {

    @Id
    @Constraints.Min(value=1)
    public Integer id;

    @Constraints.Required
    @Constraints.MaxLength(value=20)
    public String name;

    @Constraints.Required
    @Constraints.MaxLength(value=25)
    public String image;

    public Level() {
    }

    /**
     * Find a level by id.
     */
    public static Level findById(Integer id) {
        return JPA.em().find(Level.class, id);
    }

    /**
    * Find a level by name
    */
    public static Level findByName(String name) {
        Level level = (Level)JPA.em()
            .createQuery("from Level where lower(name) = :n")
            .setParameter("n", name.toLowerCase())
            .getSingleResult();

        return level;
    }
    
    /**
     * Insert this new level.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this level.
     */
    public void update() {
        JPA.em().merge(this);
    }
    
    /**
     * Delete this level.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
