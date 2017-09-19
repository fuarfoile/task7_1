import com.boast.dao.DaoFactory;
import com.boast.dao.GenericDao;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.Department;
import com.boast.transferobject.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.List;

public class MySqlEmployeeDaoTest {

    @Test
    public void testGetAll() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        List<Employee> list;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Employee> dao = daoFactory.getEmployeeDao(con);
            list = dao.getAll();
        }

        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        Employee employee;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Employee> dao = daoFactory.getEmployeeDao(con);
            employee = dao.getById(0);
        }
        Assert.assertNotNull(employee);
    }

    @Test
    public void testCreate() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        boolean res;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Employee> dao = daoFactory.getEmployeeDao(con);
            Employee employee = new Employee();
            employee.setId(100);
            employee.setName("test name");
            employee.setSurname("test surname");
            employee.setPosition("test position");

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.create(employee);
            con.rollback(svpt);
            con.setAutoCommit(true);
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testUpdate() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        boolean res;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Employee> dao = daoFactory.getEmployeeDao(con);
            Employee employee = new Employee();
            employee.setId(0);
            employee.setPosition("test position");

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.update(employee);
            con.rollback(svpt);
            con.setAutoCommit(true);
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testDelete() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        boolean res;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Employee> dao = daoFactory.getEmployeeDao(con);
            Employee employee = new Employee();
            employee.setId(0);

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.delete(employee);
            con.rollback(svpt);
            con.setAutoCommit(true);
        }
        Assert.assertTrue(res);
    }
}
