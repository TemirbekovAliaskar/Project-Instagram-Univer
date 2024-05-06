package instagram.Api;

import instagram.entity.Post;
import instagram.entity.User;
import instagram.exception.MyException;
import instagram.service.PostService;
import instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/reg")
@RequiredArgsConstructor
public class RegApi {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/page")
    public String signPage(Model model) {
        model.addAttribute("login", new User());
        return "sign-page";
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute("login") User user, Model model) {
        try {
            User currentUser = userService.findUserByNameAndPassword(user);
            return checkingSome(model, currentUser.getId());
        } catch (MyException e) {
            model.addAttribute("errorMessage", "Incorrect userName or password, Please white correct");
            return "error-page";
        }
    }
    @GetMapping("/main/{userId}")
    private String checkingSome(Model model, @PathVariable Long userId){
        List<User> users = userService.subscriptionsOfUser(userId);
        List<Post> allPosts = postService.findAllPosts();
        Collections.reverse(allPosts);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("userId", userId);
        model.addAttribute("subscriptions", users);
        return "home-page";
    }


    @GetMapping("/new")
    public String createAccount(Model model) {
        model.addAttribute("newUser", new User());
        return "new-user";
    }

    @PostMapping("/save")
    public String signUp(@ModelAttribute("newUser") User user, Model model) {
        try {
            userService.signUp(user);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Duplicate email or username. Please choose another one.");
            return "error-page";
        }
        return "redirect:/reg/page";
    }

    @GetMapping("/delUserWIthPass/{userId}")
    public String passwordForDelete(@PathVariable Long userId,
                                    Model model) {
        model.addAttribute("curUser", userId);
        return "deleteUser-page";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUserById(@PathVariable Long userId,
                                 @RequestParam String password,
                                 Model model) {
        try {
            userService.deleteUserByPass(password, userId);
            return "redirect:/reg/page";
        } catch (MyException e) {
            model.addAttribute("errorMessage", "Incorrect password, Please white correct");
            return "error-page";
        }
    }
}

