package models;

import play.db.jpa.JPA;
import javax.persistence.*;
import play.data.validation.Constraints;

import java.util.List;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Profile entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "task_seq", sequenceName = "task_seq")
public class Task implements PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    public Long id;

    @Constraints.Required
    @Constraints.MaxLength(value=200)
    public String title;

    @Constraints.Required
    public Integer level;
    
    @Constraints.Required
    public Date datetime;

    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer liked;
    
    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer disliked;

    public Task() {
    }

    /**
    * Default constructor for adding new task
    * that set title of task and it's level
    */
    public Task(String title, Integer level) {
        this.title = title;
        this.level = level;
        this.datetime = new Date();
        this.liked = 0;
        this.disliked = 0;
    }

    /**
     * Find a task by id.
     */
    public static Task findById(Long id) {
        return JPA.em().find(Task.class, id);
    }

    /**
    * Find a task by level
    */
    public static List<Task> findByLevel(Long profileId, Integer level, Integer page) {
        List<Task> listOfTasks = JPA.em()
            .createQuery("from Task where level <= :lev and datetime <= CURRENT_DATE and id not in (select taskId from UserTask where profileId = :pi) order by datetime desc")
            .setParameter("lev", level)
            .setParameter("pi", profileId)
            .setFirstResult((page - 1) * PAGESIZE)
            .setMaxResults(PAGESIZE)
            .getResultList();

        return listOfTasks;
    }
    
    /**
     * Insert this new task.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this task.
     */
    public void update() {
        JPA.em().merge(this);
    }
    
    /**
     * Delete this task.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
