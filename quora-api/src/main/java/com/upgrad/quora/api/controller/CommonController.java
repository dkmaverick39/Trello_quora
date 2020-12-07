package com.upgrad.quora.api.controller; 
 
import com.upgrad.quora.service.business.UserBusinessService; 
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException; 
import com.upgrad.quora.service.exception.UserNotFoundException; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.MediaType; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
import com.upgrad.quora.api.model.UserDetailsResponse; 
 
@RestController 
@RequestMapping("/") 
public class CommonController { 
	
	
    @Autowired 
    private UserBusinessService userBusinessService; 
    
	@RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("userId") final String userUUId,
			@RequestHeader("authorization") final String accessToken)
			throws AuthorizationFailedException, UserNotFoundException {
         
    	 final UserEntity user = userBusinessService.getUserProfile(userUUId, accessToken); 
 
         UserDetailsResponse userDetailsResponse = new UserDetailsResponse(); 
         userDetailsResponse.firstName(user.getFirstname()); 
         userDetailsResponse.lastName(user.getLastname()); 
         userDetailsResponse.userName(user.getUsername()); 
         userDetailsResponse.emailAddress(user.getEmail()); 
         userDetailsResponse.country(user.getCountry()); 
         userDetailsResponse.aboutMe(user.getAboutme()); 
         userDetailsResponse.dob(user.getDob()); 
         userDetailsResponse.contactNumber(user.getContactnumber()); 
 
         return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK); 
     } 
 } 
