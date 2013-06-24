package models;

import java.util.List;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

import java.math.BigInteger;

/**
 * Follower entity managed by JPA
 */
@Entity 
public class Follower implements PageView {

    @Id
    public BigInteger id;

    @Constraints.Required
    public Long profileId;

    @Constraints.Required
    public Long follow;

    public Follower() {
    }

    /**
    * Default constructor for adding new follower
    * that set profileId and follow
    */
    public Follower(Long profileId, Long follow) {
        this.profileId = profileId;
        this.follow = follow;
    }

    /**
    * Find a follower by Id
    */
    public static Checks Follower(BigInteger id) {
        return JPA.em().find(Checks.class, id);
    }

    /**
    * Find followers for current user
    */
    public static List<Follower> findFollowers(Long profileId, Integer page) {
        List<Follower> listOfFollowers = JPA.em()
        .createQuery("from Follower where profileId = ?")
        .setParameter(1, profileId)
        .setFirstResult((page - 1) * PAGESIZE)
        .setMaxResults(PAGESIZE)
        .getResultList();

        return listOfFollowers;
    }
    
    /**
     * Insert this new follower.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this follower.
     */
    public void update(BigInteger id) {
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Delete this follower.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
