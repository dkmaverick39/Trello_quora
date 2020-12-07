package com.upgrad.quora.service.business; 
 
import com.upgrad.quora.service.dao.UserDao; 
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException; 
import com.upgrad.quora.service.exception.UserNotFoundException; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation; 
import org.springframework.transaction.annotation.Transactional; 
 
@Service 
public class AdminBusinessService { 
 
     @Autowired 
     private UserDao userDao; 

 
     @Transactional(propagation = Propagation.REQUIRED) 
     public String deleteUser(String userUUId, String accessToken) throws AuthorizationFailedException, UserNotFoundException { 
         
    	 UserAuthEntity userAuthEntity = userDao.getUserAuthByToken(accessToken);
    	 
    	 if(userAuthEntity == null) {
             throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
         }

         if(userAuthEntity.getLogoutAt() != null) {
             throw new AuthorizationFailedException("ATHR-002", "User is signed out");
         }

         if (!"ADMIN".equalsIgnoreCase(userAuthEntity.getUserId().getRole())) { 
             throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin"); 
         } 
         
         UserEntity user = userDao.getUserById(userUUId); 
         if (user == null) { 
             throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist"); 
         } 
         
         userDao.deleteUser(user); 
         return user.getUuid(); 
 
     }


}
