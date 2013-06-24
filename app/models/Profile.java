package models;

import play.db.jpa.JPA;
import javax.persistence.*;
import play.data.validation.Constraints;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Profile entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    public Long id;

    @Constraints.Email
    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;
    
    public String username;

    public String first_name;
    
    public String last_name;
    
    public Date birthday;
    
    public Character gender;
    
    @Constraints.Min(value=1)
    public Integer level;

    @Constraints.Min(value=0)
    public Integer points;

    @Constraints.Min(value=0)
    public Integer completed;

    @Constraints.Min(value=0)
    public Integer todo_list;
    
    @Constraints.MaxLength(value=500)
    public String status;
    
    @Constraints.MaxLength(value=30)
    public String avatar;

    public Profile() {
    }

    /**
    * Default constructor for registration
    * that set default values for fields with constraints
    * and set specified values of email and password
    */
    public Profile(String email, String password) {
        this.email = email.toLowerCase();
        this.password = password;
        this.level  = 1;
        this.points = 0;
        this.completed = 0;
        this.todo_list = 0;
    }

    /**
     * Find a profile by id.
     */
    public static Profile findById(Long id) {
        return JPA.em().find(Profile.class, id);
    }

    /**
    * Find a profile by email
    */
    public static Profile findByEmail(String email) {
        return (Profile) JPA.em()
            .createQuery("from Profile where lower(email) = :em")
            .setParameter("em", email.toLowerCase())
            .getSingleResult();
    }
    
    /**
     * Insert this new profile.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this profile.
     */
    public void update() {
        JPA.em().merge(this);
    }
    
    /**
     * Delete this profile.
     */
    public void delete() {
        JPA.em().remove(this);
    }

    /**
    * Special profile without email and password
    * for secure data transfer
    */
    public static class ProfileSafe {
        public Long id;
        public String username;
        public String first_name;
        public String last_name;
        public Date birthday;
        public Character gender;
        public Integer level;
        public Integer points;
        public Integer completed;
        public Integer todo_list;
        public String status;
        public String avatar;


        public ProfileSafe() {
        }

        public ProfileSafe(Profile p) {
            this.id = p.id;
            this.username = p.username;
            this.first_name = p.first_name;
            this.last_name = p.last_name;
            this.birthday = p.birthday;
            this.gender = p.gender;
            this.level = p.level;
            this.points = p.points;
            this.completed = p.completed;
            this.todo_list = p.todo_list;
            this.status = p.status;
            this.avatar = p.avatar;
        }
    }

}
