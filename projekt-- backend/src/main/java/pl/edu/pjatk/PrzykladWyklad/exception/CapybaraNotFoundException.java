package pl.edu.pjatk.PrzykladWyklad.exception;

public class CapybaraNotFoundException extends RuntimeException{
    public CapybaraNotFoundException()
    {
        super("Capybara not found");
    }
}
