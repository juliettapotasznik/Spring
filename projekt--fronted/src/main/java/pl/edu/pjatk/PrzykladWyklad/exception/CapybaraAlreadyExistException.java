package pl.edu.pjatk.PrzykladWyklad.exception;

public class CapybaraAlreadyExistException extends RuntimeException {
    public CapybaraAlreadyExistException()
    {
        super("Capybara already exist!");
    }
}
