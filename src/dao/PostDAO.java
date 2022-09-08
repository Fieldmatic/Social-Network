package dao;

import dto.NewPostDTO;
import model.Comment;
import model.Post;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

public class PostDAO {
    public List<Post> posts;
    private UserDAO userDAO;

    public PostDAO(UserDAO userDAO){
        this.posts = new ArrayList<>();
        this.userDAO = userDAO;
    }


    public void load(){
        File file = new File("data/csv/Post.csv");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    posts.add(getPostFromRow(row));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found");
        }
    }

    private Post getPostFromRow(String[] row) {
        List<String> likes = new ArrayList<>();
        Integer id = Integer.valueOf(row[0]);
        User user = userDAO.getUserByUsername(row[1]);
        String image = row[2];
        String text = row[3];
        try {
            if (!row[4].equals("")) {
                if (row[4].contains("-")) {
                    String[] likesArray = row[4].split("-");
                    likes.addAll(Arrays.asList(likesArray));
                } else {
                    likes.add(row[4]);
                }
            }
        } catch (PatternSyntaxException e) {
        }
        Boolean deleted = Boolean.valueOf(row[5]);
        Post post = new Post(id, row[1], user.getName(), user.getSurname(), user.getProfilePicture(), image, text, likes, deleted);
        if (!deleted) user.getPosts().add(post);
        return post;
    }

    public void serialize(){
        try {
            StringBuilder inputBuffer = new StringBuilder();
            inputBuffer.append("id,userId,picture,text,likes,deleted\n");
            for(Post post : posts) {
                inputBuffer.append(post.toRow());
                inputBuffer.append("\n");
            }
            FileOutputStream fileOut = new FileOutputStream("data/csv/Post.csv");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public List<String> getPhotos(User user){
        List<String> photos = new ArrayList<>();
        for(Post post : posts) {
            if (post.getOwnerUsername().equals(user.getUsername()) && !Objects.equals(post.getPicture(), "")) photos.add(post.getPicture());
        }
        return photos;
    }

    public void createFromDTO(NewPostDTO dto, User user) {
        Post post = new Post(posts.get(posts.size()-1).getId() +1, user.getUsername(), user.getName(), user.getSurname(), user.getProfilePicture(), dto.getPictureName(), dto.getText(), new ArrayList<>(), false);
        posts.add(post);
        serialize();
    }

    public Post getPostById(Integer id) {
        for (Post post : posts) {
            if (Objects.equals(post.getId(), id)) return post;
        }
        return null;
    }

    public List<Post> getAllPosts() {
        return this.posts;
    }

    public List<Post> getUserFeedPosts(User user) {
        ArrayList<Post> postsForUserFeed = new ArrayList<>();
        for (Post post: posts) {
            if (!post.getOwnerUsername().equals(user.getUsername())) {
                User postOwner = this.userDAO.getUserByUsername(post.getOwnerUsername());
                if (user.getFriends().contains(post.getOwnerUsername()) || !postOwner.getPrivateAccount()) {
                    postsForUserFeed.add(post);
                }
            }
        }
        return postsForUserFeed;
    }

    public List<Post> getUserPosts(User user) {
        ArrayList<Post> userPosts = new ArrayList<>();
        for (Post post: posts) {
            if (post.getOwnerUsername().equals(user.getUsername())) {
                    userPosts.add(post);
            }
        }
        return userPosts;
    }

    public Post deletePost(Integer postId) {
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                post.setDeleted(true);
                serialize();
                return post;
            }
        }
        return null;
    }

    public void leaveLike(Integer postId, String username) {
        Post post = getPostById(postId);
        post.getLikes().add(username);
        serialize();
    }

    public void leaveDislike(Integer postId, String username) {
        Post post = getPostById(postId);
        post.getLikes().remove(username);
        serialize();
    }
}
