package edu.northeastern.g15finalproject;

public class MessageBoardItem {

    private final String postTitle;

    private final String postBody;
    private final String postTime;
    private final String postNumComments;
    private final String postNumPlusOne;
    private final String postUser;

    private final String postID;

    public MessageBoardItem(String postTitle, String postBody, String postTime, String postNumComments, String postNumPlusOne, String postUser, String postID) {
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postTime = postTime;
        this.postNumComments = postNumComments;
        this.postNumPlusOne = postNumPlusOne;
        this.postUser = postUser;
        this.postID = postID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostHeadline() {
        // Return the first 50 characters of the post body
        return postBody.substring(0, Math.min(postBody.length(), 50)) + "...";
    }

    public long getPostTime() {

        if (postTime == null) {
            System.out.println("postTime is null");
            System.out.println(this.toString());
        }

        try {
            return Long.parseLong(postTime);
        } catch (NumberFormatException e) {
            System.out.println("postTime is not a number");
            System.out.println(e.getMessage());
        }

        System.out.println("postTime is not null");
        System.out.println(this.toString());
        System.out.flush();
        return Long.getLong(postTime);
    }

    public String getPostNumComments() {
        return postNumComments;
    }

    public String getPostNumPlusOne() {
        return postNumPlusOne;
    }

    public String getPostUser() {
        return postUser;
    }

    public String getPostID() {
        return postID;
    }

    @Override
    public String toString() {
        return "MessageBoardItem{" +
                "postTitle='" + postTitle + '\'' +
                ", postBody='" + postBody + '\'' +
                ", postTime='" + postTime + '\'' +
                ", postNumComments='" + postNumComments + '\'' +
                ", postNumPlusOne='" + postNumPlusOne + '\'' +
                ", postUser='" + postUser + '\'' +
                ", postID='" + postID + '\'' +
                '}';
    }
}
