package medinaPharma.aiac.products.exception.handler;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
