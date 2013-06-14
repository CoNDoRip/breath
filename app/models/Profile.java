package models;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import play.db.jpa.*;

import java.util.Date;

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
    
    public char gender;
    
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

    /**
     * Find a profile by id.
     */
    public static Profile findById(Long id) {
        return JPA.em().find(Profile.class, id);
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

    public static String login(String email, String password) {
        Query q = JPA.em().createQuery("SELECT first_name FROM Profile WHERE lower(email) = :em and password = :pw");
        q.setParameter("em", email.toLowerCase());
        q.setParameter("pw", password);
        String name = (String)q.getSingleResult();
        return name;
    }

}
