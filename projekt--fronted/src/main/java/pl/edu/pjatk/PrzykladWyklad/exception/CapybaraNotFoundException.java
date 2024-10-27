package pl.edu.pjatk.PrzykladWyklad.exception;

public class CapybaraNotFoundException extends RuntimeException{
    public CapybaraNotFoundException(String s)
    {
        super("Capybara not found");
    }
}
