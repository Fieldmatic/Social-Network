package dao;

import model.Post;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
       // loadPostsForUsers();
    }

   /* private void loadPostsForUsers() {
        for (User user : userDAO.users) {
            List<Post> userPosts = new ArrayList<>();
            for (Post post : posts) {
                if (user.getUsername().equals(post.getUser().getUsername())) {
                    userPosts.add(post);
                }
            }
            user.setPosts(userPosts);
        }
    }*/

    private Post getPostFromRow(String[] row) {
        Integer id = Integer.valueOf(row[0]);
        User user = userDAO.getUserByUsername(row[1]);
        String image = row[2];
        String text = row[3];
        Boolean deleted = Boolean.valueOf(row[4]);
        Post post = new Post(id,row[1],image,text,deleted);
        if (!deleted) user.getPosts().add(post);
        return post;
    }

    public Post getPostById(Integer id) {
        for (Post post : posts) {
            if (Objects.equals(post.getId(), id)) return post;
        }
        return null;
    }
}
