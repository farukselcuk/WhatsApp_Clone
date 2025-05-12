package tr.com.ind.exception;

public class FriendRequestAlreadySentException extends RuntimeException {
    public FriendRequestAlreadySentException(String message) {
        super(message);
    }
}
