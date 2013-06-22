package models;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

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
        Profile profile = (Profile)JPA.em().createQuery(
            "from Profile where lower(email) = :em"
            ).setParameter("em", email.toLowerCase())
             .getSingleResult();

        return profile;
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
    public void update(Long id) {
        this.id = id;
        JPA.em().merge(this);
    }
    
    /**
     * Delete this profile.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
