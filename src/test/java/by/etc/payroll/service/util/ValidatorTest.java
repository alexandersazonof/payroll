package by.etc.payroll.service.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    @Test
    public void validateStringExpression() {
        String actual = "Simple string";
        boolean expected = true;

        assertEquals("Result validate string", expected, Validator.validateString(actual));
    }

    @Test
    public void validateNumberExpression() {
        String actual = "BY12344578";
        boolean expected = true;

        assertEquals("Result validate number", expected, Validator.validateNumber(actual));
    }

    @Test
    public void validatePasswordExpression() {
        String actual = "Password1234";
        boolean expected = true;

        assertEquals("Result validate password", expected, Validator.validatePassword(actual));
    }

    @Test
    public void validateEmailExpression() {
        String actual = "example@mail.com";
        boolean expected = true;

        assertEquals("Result validate email", expected, Validator.validateEmail(actual));
    }

}
