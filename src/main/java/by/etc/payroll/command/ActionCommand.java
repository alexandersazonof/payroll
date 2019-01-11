package by.etc.payroll.command;

import by.etc.payroll.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ActionCommand {
    void execute (HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException;
}
