package dao;

import model.Post;

public class Repository {
    private UserDAO userDAO;

    private PostDAO postDAO;

    private CommentDAO commentDAO;

    private FriendRequestDAO friendRequestDAO;

    public Repository(){
        this.userDAO = new UserDAO();
        this.postDAO = new PostDAO(this.userDAO);
        this.commentDAO = new CommentDAO(this.userDAO, this.postDAO);
        this.friendRequestDAO = new FriendRequestDAO(this.userDAO);
    }

    public void load(){
        userDAO.load();
        postDAO.load();
        commentDAO.load();
        friendRequestDAO.load();
    }


    public FriendRequestDAO getFriendRequestDAO() {
        return friendRequestDAO;
    }

    public void setFriendRequestDAO(FriendRequestDAO friendRequestDAO) {
        this.friendRequestDAO = friendRequestDAO;
    }

    public CommentDAO getCommentDAO() {
        return commentDAO;
    }

    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public PostDAO getPostDAO() {
        return postDAO;
    }

    public void setPostDAO(PostDAO postDAO) {
        this.postDAO = postDAO;
    }
}
