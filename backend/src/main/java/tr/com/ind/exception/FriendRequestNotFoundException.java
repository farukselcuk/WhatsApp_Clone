package tr.com.ind.exception;

public class FriendRequestNotFoundException extends RuntimeException{
    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
