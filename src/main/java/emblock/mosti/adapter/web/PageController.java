package emblock.mosti.adapter.web;

import emblock.mosti.application.domain.TokenInfo;
import emblock.mosti.application.dto.request.user.UserLoginDto;
import emblock.mosti.application.security.jwt.JWTInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("/page")
public class PageController {
    public static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @GetMapping("/home")
    public String homeView(Model model, Principal principal) {
        return "home";
    }

    @GetMapping("/main")
    String mainView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "main";
    }

    @GetMapping("/index")
    String indexView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "index";
    }

    @GetMapping("/login")
    String viewLogin(Model model) {
        return "login";
    }

    @GetMapping("/log")
    String viewLog(Model model) {
        return "log";
    }

    @GetMapping("/notice")
    String viewNotice(Model model) {
        return "notice";
    }


    @GetMapping("/logout")
    String viewLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/page/login?logout=true";
    }

    @GetMapping("/token-validation")
    String tokenValidationView(Model model){
        //log.debug(LogHelper.printEntity(principal))
        return "token_validation";
    }

    @GetMapping("/user")
    String userView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "user";
    }

    @GetMapping("/school")
    String schoolView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "school";
    }

    @GetMapping("/certified")
    String certifiedView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "certified";
    }

    @GetMapping("/issue")
    String issueView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "issue";
    }
    @GetMapping("/burn")
    String burnView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "burn";
    }
    @GetMapping("/student")
    String studentView(Model model, Principal principal){
        //log.debug(LogHelper.printEntity(principal))
        return "student";
    }

    @GetMapping("/token-info")
    String tokenInfoView() {
        return "token_info";
    }


}
