package dao;

import model.Comment;
import model.Post;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    public List<Comment> comments;
    private UserDAO userDAO;

    private PostDAO postDAO;

    public CommentDAO(UserDAO userDAO, PostDAO postDAO){
        this.comments = new ArrayList<>();
        this.userDAO = userDAO;
        this.postDAO = postDAO;
    }

    public void load(){
        File file = new File("data/csv/Comment.csv");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    comments.add(getCommentFromRow(row));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found");
        }
    }

    private Comment getCommentFromRow(String[] row) {
        Integer id = Integer.valueOf(row[0]);
        Post post = postDAO.getPostById(Integer.valueOf(row[1]));
        String text = row[3];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(row[4],formatter);
        LocalDateTime dateEdited = LocalDateTime.parse(row[5],formatter);
        Boolean deleted = Boolean.valueOf(row[6]);
        Comment comment = new Comment(id,row[2],Integer.valueOf(row[1]),text,dateTime, dateEdited, deleted);
        if (!deleted) post.getComments().add(comment);
        return comment;
    }
}
