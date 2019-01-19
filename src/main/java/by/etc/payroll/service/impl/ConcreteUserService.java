package by.etc.payroll.service.impl;


import by.etc.payroll.bean.BankAccount;
import by.etc.payroll.bean.Card;
import by.etc.payroll.bean.User;

import by.etc.payroll.dao.factory.DaoFactory;
import by.etc.payroll.dao.impl.SqlBankAccountDAO;
import by.etc.payroll.dao.impl.SqlCardDAO;
import by.etc.payroll.dao.impl.SqlUserDAO;
import by.etc.payroll.service.exception.*;
import by.etc.payroll.service.util.Validator;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.service.AbstractUserService;
import by.etc.payroll.service.util.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;


public class ConcreteUserService implements AbstractUserService {
    private static Logger LOG = LogManager.getLogger(ConcreteUserService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();


    private final String FIELD_LOGIN = "username";
    private final String FIELD_PASSWORD = "password";
    private final String FIELD_CONFIRM_PASSWORD = "password_confirm";
    private final String FIELD_EMAIL = "email";
    private final String FIELD_FIRST_NAME = "firstName";
    private final String FIELD_LAST_NAME = "lastName";

    private final String STATUS = "Guest";

    @Override
    public void registration(User user) throws ServiceException {


        if (!Validator.validateLogin(user.getLogin())) {
            throw new ServiceWrongLoginException();
        }



        if (!Validator.validatePassword(user.getPassword())) {
            throw new ServiceWrongPasswordException();
        }


        if (!Validator.validateEmail(user.getEmail())) {
            System.out.println(user.getEmail());
            throw new ServiceWrongEmailException();
        }



        if (!Validator.validateName(user.getFirstName()) ||
                !Validator.validateName(user.getLastName())) {

            throw new ServiceException();
        }



        try {

            SqlUserDAO userDAO = daoFactory.getUserDAO();

            User userWithThisLogin = userDAO.findByLogin(user.getLogin());
            if (userWithThisLogin != null) {
                throw new ServiceWrongLoginException();
            }

            if (userDAO.findByEmail(user.getEmail()) != null) {
                throw new ServiceWrongEmailException();
            }

            String encryptPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(encryptPassword);
            user.setRole(Roles.USER);

            userDAO.insert(user);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public User logIn(String login, String password) throws ServiceException {

        if (!Validator.validateLogin(login)) {

            throw new ServiceWrongLoginException();
        }

        if (!Validator.validateString(password)) {

            throw new ServiceWrongPasswordException();
        }

        try {

            SqlUserDAO sqlUserDAO = daoFactory.getUserDAO();

            User user = sqlUserDAO.findByLogin(login);

            if (user == null) {

                throw new ServiceWrongLoginException();
            }
            if (!BCrypt.checkpw(password, user.getPassword())) {

                throw new ServiceWrongPasswordException();
            }

            return user;
        } catch (DAOException e) {

            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User save(int id, String login, String email, String firstName, String lastName, String newPassword, String confirmPassword, String oldPassword) throws ServiceException {

        SqlUserDAO userDAO = daoFactory.getUserDAO();

        try {

            if (!Validator.validateLogin(login) || !(userDAO.findByLoginNotConsideringId(login, id) == null)) {
                throw new ServiceWrongLoginException();
            }


            if (!Validator.validateEmail(email) || !(userDAO.findByEmailNotConsideringId(email, id) == null)) {
                throw new ServiceWrongEmailException();
            }

            if (!Validator.validateName(firstName) || !Validator.validateName(lastName)) {
                throw new ServiceWrongNameException();
            }



            User user;

            if (!BCrypt.checkpw(oldPassword, userDAO.find(id).getPassword())) {
                throw new ServiceWrongPasswordException();
            }

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {

                user = create(id, login,BCrypt.hashpw(oldPassword, BCrypt.gensalt()), email, lastName, firstName);


            } else if (!Validator.validatePassword(newPassword) || !Validator.validatePassword(confirmPassword) || !newPassword.equals(confirmPassword)) {

                throw new ServiceWrongPasswordConfirmExceprion();
            } else {
                user = create(id, login, BCrypt.hashpw(newPassword, BCrypt.gensalt()), email, lastName, firstName);
            }


            userDAO.update(user);

            return user;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public List<BankAccount> getListBankAccountByUser(User user) throws ServiceException {


        if (user == null || !user.getRole().equalsIgnoreCase(Roles.USER)) {
            throw new ServiceUnauthorizedAccessException();
        }


        try {

            SqlBankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();


            List<BankAccount> bankAccountList = bankAccountDAO.getAllByUserID(user.getId());

            SqlCardDAO cardDAO = daoFactory.getCardDAO();
            for (BankAccount b : bankAccountList) {
                List<Card> cardList = cardDAO.getAllByAccountId(b.getId());
                b.setCardList(cardList);
            }

            return bankAccountList;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }



    private User create (int id, String login, String password, String email, String lastName, String firstName)  {
        User user = new User();

        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setRole(STATUS);
        return user;
    }
}


