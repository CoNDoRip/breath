package models;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * UserTask entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "usertask_seq", sequenceName = "usertask_seq")
public class UserTask implements PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usertask_seq")
    public Long id; 

    @Constraints.Required
    public Long profileId;

    @Constraints.Required
    public Long taskId;
    
    @Constraints.Required
    public Date datetime;

    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer approved;
    
    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer rejected;

    @Constraints.Required
    public String status;

    @Constraints.Required
    @Constraints.MaxLength(value=100)
    public String image;
    
    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer liked;

    public UserTask() {
    }

    /**
    * Default constructor for adding new UserTask
    * that set profileId, taskId and name of image
    */
    public UserTask(Long profileId, Long taskId, String image) {
        this.datetime = new Date();
        this.approved = 0;
        this.rejected = 0;
        this.status = "pending";
        this.image = image;
        this.liked = 0;
    }

    /**
    * Copy constructor
    */
    public UserTask(UserTask ut) {
        this.id = ut.id;
        this.profileId = ut.profileId;
        this.taskId = ut.taskId;
        this.datetime = ut.datetime;
        this.approved = ut.approved;
        this.rejected = ut.rejected;
        this.status = ut.status;
        this.image = ut.image;
        this.liked = ut.liked;
    }

    /**
     * Find a UserTask by id.
     */
    public static UserTask findById(Long id) {
        return JPA.em().find(UserTask.class, id);
    }

    /**
    * Find a UserTask by profileId
    */
    public static List<UserTaskWithTitle> findByProfileId(Long profileId, Integer page) {
        List<UserTask> listOfUserTasks = JPA.em()
        .createQuery("from UserTask where profileId = :pi order by datetime desc")
        .setParameter("pi", profileId)
        .setFirstResult((page - 1) * PAGESIZE)
        .setMaxResults(PAGESIZE)
        .getResultList();

        return UserTaskWithTitle.getListOfUserTasksWithTitle(listOfUserTasks);
    }
    
    /**
     * Insert this new UserTask.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this UserTask.
     */
    public void update(Long id) {
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Delete this UserTask.
     */
    public void delete() {
        JPA.em().remove(this);
    }

    /**
    * Enumeration of UserTask statuses
    */
    private enum UserTaskStatus {
        
          REJECTED("rejected")
        , PENDING("pending")
        , APPROVED("approved");
        ;

        private String status;

        UserTaskStatus(String status) {this.status = status;}

        String getStatus() {return status;}
    }

    public static class UserTaskWithTitle extends UserTask {

        public String title;

        UserTaskWithTitle(UserTask ut) {
            super(ut);
            title = (String) JPA.em()
            .createQuery("select title from Task where id = ?")
            .setParameter(1, ut.taskId)
            .getSingleResult();
        }

        public static List<UserTaskWithTitle> getListOfUserTasksWithTitle(List<UserTask> listOfUserTasks) {
            List<UserTaskWithTitle> listOfUserTasksWithTitle = new ArrayList<UserTaskWithTitle>(PAGESIZE);

            for(UserTask ut: listOfUserTasks)
                listOfUserTasksWithTitle.add(new UserTaskWithTitle(ut));

            return listOfUserTasksWithTitle;
        }
    }

}
