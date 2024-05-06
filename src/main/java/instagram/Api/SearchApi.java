package instagram.Api;

import instagram.entity.Post;
import instagram.entity.User;
import instagram.service.FollowerService;
import instagram.service.PostService;
import instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchApi {

    private final UserService userService;
    private final FollowerService followerService;
    private final PostService postService;

    @GetMapping("/createSearch/{userId}")
    public String createSearch(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "search-page";
    }

    @PostMapping("/mSearch/{userId}")
    public String pageTOSearch(@PathVariable Long userId,
                               @RequestParam String keyword, Model model) {
        List<User> findUsers = userService.findUserByUserName(userId, keyword);
        model.addAttribute("users", findUsers);
        model.addAttribute("userId", userId);
        return "search-page";
    }

    @GetMapping("/otherUser/{userId}/{otherUserId}")
    public String otherUser(@PathVariable Long userId,
                            @PathVariable Long otherUserId,
                            Model model) {
        User foundUser = userService.findById(otherUserId);
        User currentUser = userService.findById(userId);
        if (foundUser.getUserName().equalsIgnoreCase(currentUser.getUserName())){
            return "redirect:/home/profUser/"+ userId;
        }else {
            List<Post> postList = foundUser.getPosts();
            List<Post> posts = new ArrayList<>(postList);
            Collections.reverse(posts);
            int subscribers = followerService.getNumberOfSubscribers(otherUserId);
            int subscriptions = followerService.getNumberOfSubscriptions(otherUserId);
            model.addAttribute("userId", userId);
            model.addAttribute("currentUser", foundUser);
            model.addAttribute("subscribers", subscribers);
            model.addAttribute("subscriptions", subscriptions);
            model.addAttribute("posts", posts);
            return "otherUser-page";
        }
    }

    @GetMapping("/addSubscriber/{userId}/{otherUserId}")
    public String addSubscriber(@PathVariable Long userId,
                                @PathVariable Long otherUserId) {
        followerService.addSubscriber(userId, otherUserId);
        return "redirect:/search/otherUser/" + userId + "/" + otherUserId;
    }

    @GetMapping("/subscriber/{userId}/{otherUserId}")
    public String subscriber(@PathVariable Long userId,
                                @PathVariable Long otherUserId) {
        followerService.addSubscriber(userId, otherUserId);
        return "redirect:/home/someUser/" + userId + "/" + otherUserId;
    }

    @GetMapping("/likes/{userId}/{otherId}/{postId}")
    public String isLike(@PathVariable Long postId,
                         @PathVariable Long userId,
                         @PathVariable Long otherId) {
        postService.getLikePost(userId, postId);
        return "redirect:/search/otherUser/" + userId + "/" + otherId;
    }

}

