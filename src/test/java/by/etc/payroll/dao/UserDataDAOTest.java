package by.etc.payroll.dao;

import by.etc.payroll.bean.UserData;
import by.etc.payroll.dao.exception.DAOException;
import by.etc.payroll.dao.factory.DaoFactory;
import org.junit.*;

public class UserDataDAOTest {
    private final UserDataDAO userDataDAO = DaoFactory.getInstance().getUserDataDAO();

    private UserData userData;

    @Before
    public void setupUserDate() {
        userData = new UserData();
        userData.setIdCard(11);
        userData.setCity("Minsk");
        userData.setAddress("Wall-street");
        userData.setLastName("Ivanov");
        userData.setFirstName("Peter");
    }

    @After
    public void removeUserData() {
        try {
            UserData userDataByLastName = userDataDAO.findByLastName(userData.getLastName());

            if (userDataByLastName != null) {
                userDataDAO.deleteByLastName(userData.getLastName());
            }

        } catch (DAOException e) {
            throw new RuntimeException("Error during executing @After ", e);
        }
    }

    @Test
    public void insertTest() {
        UserData userDataFound;
        try {
            userDataDAO.insert(userData);

            userDataFound = userDataDAO.findByLastName(userData.getLastName());



        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        boolean actual = (userDataFound == null);
        boolean expected = false;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteByLastName() {
        UserData userDataFound;
        try {
            userDataDAO.insert(userData);
            userDataDAO.deleteByLastName(userData.getLastName());

            userDataFound = userDataDAO.findByLastName(userData.getLastName());


        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        boolean actual = (userDataFound == null);
        boolean expected = true;

        Assert.assertEquals(actual, expected);
    }
}
