package dev.jpfsgs.gerenciadordeprojetosv2backend.exception.custom;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
