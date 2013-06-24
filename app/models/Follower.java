package models;

import play.db.jpa.JPA;
import javax.persistence.*;
import play.data.validation.Constraints;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;

/**
 * Follower entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "follower_seq", sequenceName = "follower_seq")
public class Follower implements PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follower_seq")
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
    public static Follower findById(BigInteger id) {
        return JPA.em().find(Follower.class, id);
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
    * Get all profiles of user's followers in safe mode
    */
    public static List<Profile.ProfileSafe> getFollowers(Long profileId, Integer page) {
        List<Follower> listOfFollowers = findFollowers(profileId, page);

        List<Profile.ProfileSafe> listOfProfiles = new ArrayList<Profile.ProfileSafe>(PAGESIZE);
        for(Follower f: listOfFollowers) {
            Profile p = Profile.findById(f.follow);
            Profile.ProfileSafe ps = new Profile.ProfileSafe(p);
            listOfProfiles.add(ps);
        }
        return listOfProfiles;
    }

    /**
    * Find follower by parameters
    */
    public static Follower findByParameters(Long profileId, Long follow) {
        return (Follower) JPA.em()
            .createQuery("from Follower where profileId = :pi and follow = :fol")
            .setParameter("pi", profileId)
            .setParameter("fol", follow)
            .getSingleResult();
    }

    /**
    * Check if this user already follow him
    */
    public static boolean isFollow(Long profileId, Long follow) {
        try {
            findByParameters(profileId, follow);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
    
    /**
     * Insert this new follower.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }
    
    /**
     * Delete this follower.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
