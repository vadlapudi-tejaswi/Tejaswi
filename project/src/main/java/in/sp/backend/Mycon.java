package in.sp.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sp.entity.UserIno;
import jakarta.servlet.http.HttpSession;

@Controller
public class Mycon {
	@GetMapping("/register")
	public String openregisterpage() {
		return "register";
	}
	@GetMapping("/login")
	public String openRegisterPage() {
		return "login";
	}
	@PostMapping("/registerdata")
	public String registerData(@RequestParam("name") String name,
	                           @RequestParam("password") String password,
	                           @RequestParam("email") String email,
	                       HttpSession session) {
		session.setAttribute("name",name);
		session.setAttribute("password",password);
		session.setAttribute("email",email);
		
		return "login";
	}
	@PostMapping("/logindata")
	public String PostData(@RequestParam("name") String name,
	                       @RequestParam("password") String password,
	                       @RequestParam("email") String email,
	                       HttpSession session,
	                       Model model) {
	    String regName = (String) session.getAttribute("name");
	    String regPassword = (String) session.getAttribute("password");
	    String regEmail = (String) session.getAttribute("email");

	    if (name.equals(regName) && password.equals(regPassword) && email.equals(regEmail)) {
	        return "profile"; 
	    } else {
	        model.addAttribute("error", "Invalid login credentials");
	        return "login"; 
	    }
	}
	
}
		
		
	                   	
	
	

		


