package instagram.service;

import instagram.entity.Post;

import java.util.List;

public interface PostService {
    void createPost(Long userId, Post newPOst);

    Post findById(Long postId);

    List<Post> findAllPosts();

    void updatePOst(Long postId, Post post);

    void deletePostById(Long postId);

    void getLikePost(Long currentUserId, Long postId);
}
