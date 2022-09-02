package dao;

import model.Comment;
import model.Post;
import model.User;

import java.io.*;
import java.time.LocalDate;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(row[4],formatter);
        LocalDateTime dateEdited = LocalDateTime.parse(row[5],formatter);
        Boolean deleted = Boolean.valueOf(row[6]);
        User user = this.userDAO.getUserByUsername(row[2]);
        Comment comment = new Comment(id,user,Integer.valueOf(row[1]),text,dateTime, dateEdited, deleted);
        if (!deleted) post.getComments().add(comment);
        return comment;
    }

    public void serialize(){
        try {
            StringBuilder inputBuffer = new StringBuilder();
            inputBuffer.append("id,postId,userId,text,date,dateChanged,deleted\n");
            for(Comment comment : this.comments) {
                inputBuffer.append(comment.toRow());
                inputBuffer.append("\n");
            }
            FileOutputStream fileOut = new FileOutputStream("data/csv/Comment.csv");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void createComment(String username, Integer postId, String commentText) {
        Post post = postDAO.getPostById(Integer.valueOf(postId));
        User user = this.userDAO.getUserByUsername(username);
        Comment newComment = new Comment(this.comments.get(this.comments.size()-1).getId() + 1, user, postId, commentText, LocalDateTime.now().withSecond(0).withNano(0), LocalDateTime.now().withSecond(0).withNano(0), false);
        this.comments.add(newComment);
        post.getComments().add(newComment);
        serialize();
    }

    public boolean deleteComment(Integer commentId) {
        for (Comment comment : comments) {
            if (comment.getId().equals(commentId)) {
                comment.setDeleted(true);
                serialize();
                return true;
            }
        }
        return false;
    }
}
