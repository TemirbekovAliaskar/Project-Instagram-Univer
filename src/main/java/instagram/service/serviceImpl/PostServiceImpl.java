package instagram.service.serviceImpl;

import instagram.entity.Image;
import instagram.entity.Like;
import instagram.entity.Post;
import instagram.entity.User;
import instagram.repository.PostRepository;
import instagram.repository.UserRepository;
import instagram.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void createPost(Long userId, Post newPost){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Like like = new Like();
        newPost.setLike(like);

        // Проходимся по всем изображениям в новом посте
        for (Image image : newPost.getImages()) {
            // Связываем изображение с постом
            image.setPost(newPost);
        }

        // Добавляем новый пост к пользователю
        user.getPosts().add(newPost);
        newPost.setUser(user);

        // Сохраняем пост в репозитории
        postRepository.save(newPost);
    }


    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void updatePOst(Long postId, Post post) {
        Post findPost = findById(postId);
        findPost.setTitle(post.getTitle());
        findPost.setDescription(post.getDescription());
        postRepository.save(findPost);
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public void getLikePost(Long currentUserId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found"));

        List<Long> isLikes = post.getLike().getIsLikes();
        if (isLikes.contains(currentUserId)) {
            isLikes.remove(currentUserId);
        } else {
            isLikes.add(currentUserId);
        }
    }
}
