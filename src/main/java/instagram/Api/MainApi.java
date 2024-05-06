package instagram.Api;

import instagram.entity.Post;
import instagram.entity.User;
import instagram.exception.MyException;
import instagram.service.FollowerService;
import instagram.service.PostService;
import instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class MainApi {
    private final UserService userService;
    private final PostService postService;
    private final FollowerService followerService;
    @GetMapping("/profUser/{userId}")
    public String profilePage(Model model, @PathVariable Long userId) {
        try {
            User cerruntUser = userService.findById(userId);
            Long id = cerruntUser.getId();
            int subscribers = followerService.getNumberOfSubscribers(id);
            int subscriptions = followerService.getNumberOfSubscriptions(id);
            List<Post> posts = new ArrayList<>(cerruntUser.getPosts());
            Collections.reverse(posts);
            model.addAttribute("subscribers", subscribers);
            model.addAttribute("posts", posts);
            model.addAttribute("subscriptions", subscriptions);
            model.addAttribute("currentUser", cerruntUser);
            model.addAttribute("userId", id);
            return "profile-page";
        }catch (Exception e){
            return "error-page";
        }
    }

    @GetMapping("/editProf/{userId}")
    public String editUser(Model model, @PathVariable Long userId){
        User user = userService.findById(userId);
        model.addAttribute("current", user);
        model.addAttribute("userId", userId);
        return "edit-profile";
    }

    @PostMapping("/saveProfiles/{userId}")
    public String saveProfile(@ModelAttribute("current") User currentUser,
                              Model model,
                              @PathVariable Long userId) {
        try {
            userService.updateUser(userId, currentUser);
            return "redirect:/home/profUser/" + userId;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Duplicate email or username. Please choose another one.");
            return "error-page";
        }
    }

    @GetMapping("/like/{userId}/{postId}")
    public String Like(@PathVariable Long postId,
                       @PathVariable Long userId){
        postService.getLikePost(userId, postId);
        return "redirect:/home/profUser/" + userId;
    }

    @GetMapping("/subscribers/{userId}")
    public String subscribers(@PathVariable Long userId, Model model){
        List<User> users = userService.subscribersOfUser(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("users", users);
        return "subscribers-page";
    }

    @GetMapping("/subscriptions/{userId}")
    public String subscriptions(@PathVariable Long userId, Model model){
        List<User> users = userService.subscriptionsOfUser(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("users", users);
        return "subscriptions-page";
    }

    @GetMapping("/someUser/{userId}/{subId}")
    public String someUser(@PathVariable Long userId,
                           @PathVariable Long subId, Model model){
       try {
           User otherUser = userService.findOtherUserById(userId, subId);
           List<Post> posts = otherUser.getPosts();
           Collections.reverse(posts);
           int subscriptions = followerService.getNumberOfSubscriptions(subId);
           int subscribers = followerService.getNumberOfSubscribers(subId);
           model.addAttribute("posts", posts);
           model.addAttribute("subscriptions", subscriptions);
           model.addAttribute("subscribers", subscribers);
           model.addAttribute("currentUser", otherUser);
           model.addAttribute("userId", userId);
           return "other-page";
       } catch (MyException e) {
           return "redirect:/home/profUser/" + userId;
       }
    }

    @GetMapping("/someUserSubscriptions/{userId}/{otherUserId}")
    public String otherUserSubscriptions(@PathVariable Long userId,
                                         @PathVariable Long otherUserId, Model model){
        List<User> users = userService.subscriptionsOfUser(otherUserId);
        model.addAttribute("users", users);
        model.addAttribute("userId", userId);
        return "subscriptions-page";
    }
    @GetMapping("/someUserSubscribers/{userId}/{otherUserId}")
    public String otherUserSubscribers(@PathVariable Long userId,
                                       @PathVariable Long otherUserId, Model model){
        List<User> users = userService.subscribersOfUser(otherUserId);
        model.addAttribute("users", users);
        model.addAttribute("userId", userId);
        return "subscribers-page";
    }
}