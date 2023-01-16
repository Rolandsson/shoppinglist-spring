package nu.rolandsson.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/userSession/*")
public class UserSessionController {

  @GetMapping("error/login")
  @ResponseBody
  public String errorHandler(@RequestParam int attempts){
    return "Invalid attempt {" + attempts +  "}, <a href='/'>try again</a>";
  }

  @PostMapping("login")
  public String login(HttpSession session, @RequestParam String username, @RequestParam String password) {

    if(session.getAttribute("username") != null) {
      return "redirect:/shoppingList";
    } else {

      //if(username.equals("bob") && password.equals("123")) {
      if (username != null) {
        session.setMaxInactiveInterval(60 * 30);
        System.out.println(username);
        session.setAttribute("username", username);

        return "redirect:/shoppingList";
      } else {
        Object loginAttempts = session.getAttribute("login-attempts");
        if(loginAttempts == null) {
          loginAttempts = 0;
        }

        session.setAttribute("login-attempts", (int) loginAttempts + 1);

        return "redirect:error/login?attempts=" + ((int) loginAttempts + 1);
      }
    }
  }

  @PostMapping("logout")
  public String logout(HttpSession session) throws IOException {
    session.invalidate(); //Invalidate - empty the session
    return "redirect:/index.html";
  }
}
