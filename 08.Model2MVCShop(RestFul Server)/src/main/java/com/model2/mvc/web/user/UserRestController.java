package com.model2.mvc.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> ȸ������ RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method ���� ����
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value = "json/addUser", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) throws Exception{
		
		System.out.println("user/json/addUser : POST");
		System.out.println("[ UserRestController ] ::" + user);
		
		userService.addUser(user);
		
		return userService.getUser(user.getUserId());
	}
	
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser( @PathVariable String userId ) throws Exception{
		
		System.out.println("/user/json/getUser : GET");
		
		//Business Logic
		return userService.getUser(userId);
	}
	
	@RequestMapping(value = "json/updateUser", method = RequestMethod.POST)
	public User updateUser(@RequestBody User user, HttpSession session) throws Exception{
		
		System.out.println("/user/json/updateUser : POST");
		System.out.println("UserRestController :: " + user);
		
		userService.updateUser(user);
		
		User resultUser = userService.getUser(user.getUserId());
		System.out.println("??? :: " + resultUser);
		
		if(((User)session.getAttribute("user"))!=null) {
			String sessionId=((User)session.getAttribute("user")).getUserId();	
			if(sessionId.equals(user.getUserId())){
				session.setAttribute("user", resultUser);
			}
		}					
		
		return resultUser;
	}

	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return dbUser;
	}
	
	@RequestMapping(value = "json/checkDuplication", method=RequestMethod.POST)
	public Map checkDuplication(@RequestBody User user) throws Exception{
		
		System.out.println("/user/json/checkDuplication : POST");		
		System.out.println("userId :: " + user.getUserId());
				
		Map map = new HashMap();
		map.put("check", userService.checkDuplication(user.getUserId()));
				
		return map;
	}
	
	@RequestMapping(value = "json/listUser")
	public Map listUser(@RequestBody Search search) throws Exception{
		
		System.out.println("user/json/listUser : GET / POST");
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = userService.getUserList(search);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage :: " + resultPage);
		
		map.put("resultPage", resultPage);
		map.put("search", search);
		
		return map;
	}
}