package controllers;

import play.libs.Json;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.ok;

import play.db.jpa.Transactional;
import javax.persistence.NoResultException;

import models.Profile;
import models.Follower;

import java.util.List;

@Security.Authenticated(Secured.class)
public class FollowersPage extends Controller {

	/**
	* Get user's avaliable tasks
	*/
	@Transactional(readOnly=true)
	public static Result getFollowers(Integer page) {
		if (page > 0) {
			List<Profile.ProfileSafe> listOfFollowers = Follower.getFollowers(
				Application.getProfileId(), page
			);
			return ok(Json.toJson(listOfFollowers));
		} else {
			return Application.errorResponse(
				"Error in page number! Page must be greater than 0"
			);
		}
	}

	/**
	* Follow another profile
	*/
	@Transactional
	public static Result follow(Long follow) {
		Long profileId = Application.getProfileId();
		if (Follower.isFollow(profileId, follow)) {
			return Application.errorResponse("This profile already follow him");
		} else {
			Follower newFollower = new Follower(profileId, follow);
			newFollower.save();
			return Application.goodResponse("New follower successfully added");
		}
	}

	/**
	* Unfollow specified profile
	*/
	@Transactional
	public static Result unfollow(Long follow) {
		Long profileId = Application.getProfileId();
		try {
            Follower oldFollower = Follower.findByParameters(profileId, follow);
            oldFollower.delete();
			return Application.goodResponse("This profile successfully unfollowed");
        } catch (NoResultException e) {
			return Application.errorResponse("This profile not follow him");
        }
	}

}
