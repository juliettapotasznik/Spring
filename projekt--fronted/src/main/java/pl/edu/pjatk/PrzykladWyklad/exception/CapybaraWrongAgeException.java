package pl.edu.pjatk.PrzykladWyklad.exception;

public class CapybaraWrongAgeException extends RuntimeException {
    public CapybaraWrongAgeException()
    {
        super("Wrong age of Capybara");
    }
}
