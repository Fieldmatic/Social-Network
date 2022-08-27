package dao;

import dto.NewPostDTO;
import model.Post;
import model.User;

import java.io.*;
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
    }

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

    public void serialize(){
        try {
            StringBuilder inputBuffer = new StringBuilder();
            inputBuffer.append("id,userId,picture,text,deleted\n");
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

    public void createFromDTO(NewPostDTO dto, String username) {
        Post post = new Post(posts.get(posts.size()-1).getId() +1,username, dto.getPictureName(), dto.getText(), false);
        posts.add(post);
        serialize();
    }

    public Post getPostById(Integer id) {
        for (Post post : posts) {
            if (Objects.equals(post.getId(), id)) return post;
        }
        return null;
    }
}
