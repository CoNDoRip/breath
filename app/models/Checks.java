package models;

import play.db.jpa.JPA;
import javax.persistence.*;
import play.data.validation.Constraints;

import java.math.BigInteger;
import java.util.List;

/**
 * Checks entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "checks_seq", sequenceName = "checks_seq")
public class Checks implements PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checks_seq")
    public BigInteger id;

    @Constraints.Required
    public Long profileId;

    @Constraints.Required
    public Long usertaskId;

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
     * Insert this new check in DB
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

}
