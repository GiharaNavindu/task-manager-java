package exception;

public class InvalidTaskException extends Exception{
    public InvalidTaskException(String message){
        super(message);//The constructor calls the superclass's constructor (super(message)) to pass the message to the Exception class, enabling the message to be retrieved when the exception is thrown.
    }
}
