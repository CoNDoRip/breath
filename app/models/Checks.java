package models;

import java.util.List;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

import java.math.BigInteger;

/**
 * Checks entity managed by JPA
 */
@Entity 
public class Checks implements PageView {

    @Id
    public BigInteger id;

    @Constraints.Required
    public Long profileId;

    @Constraints.Required
    public Long usertaskId;

    @Embeddable
    class ProjectId {
        Long profileId;
        Long usertaskId;
    }

    public Checks() {
    }

    /**
    * Default constructor for adding new check
    * that set profileId and usertaskId
    */
    public Checks(Long profileId, Long usertaskId) {
        this.profileId = profileId;
        this.usertaskId = usertaskId;
    }

    /**
    * Find a check by Id
    */
    public static Checks findById(BigInteger id) {
        return JPA.em().find(Checks.class, id);
    }

    /**
    * Find checks for current user
    */
    public static List<UserTask.UserTaskWithTitle> findChecks(Long profileId, Integer page) {
        List<UserTask> listOfChecks = JPA.em()
        .createQuery("from UserTask where profileId != :pi and status = 'pending' and id not in (select usertaskId from Checks where profileId = :pi)")
        .setParameter("pi", profileId)
        .setFirstResult((page - 1) * PAGESIZE)
        .setMaxResults(PAGESIZE)
        .getResultList();

        return UserTask.UserTaskWithTitle.getListOfUserTasksWithTitle(listOfChecks);
    }
    
    /**
     * Insert this new check.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this check.
     */
    public void update(BigInteger id) {
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Delete this check.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
