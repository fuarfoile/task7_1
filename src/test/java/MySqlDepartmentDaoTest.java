import com.boast.dao.DaoFactory;
import com.boast.dao.GenericDao;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.Department;
import com.boast.transferobject.Task;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.List;

public class MySqlDepartmentDaoTest {

    @Test
    public void testGetAll() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        List<Department> list;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Department> dao = daoFactory.getDepartmentDao(con);
            list = dao.getAll();
        }
        //for (Task task : list) {
        //    System.out.println(task);
        //}
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testGetById() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        Department department;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Department> dao = daoFactory.getDepartmentDao(con);
            department = dao.getById(0);
        }
        Assert.assertNotNull(department);
    }

    @Test
    public void testCreate() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        boolean res;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Department> dao = daoFactory.getDepartmentDao(con);
            Department department = new Department();
            department.setId(100);
            department.setName("test name");
            department.setPhoneNumber("+404");

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.create(department);
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
            GenericDao<Department> dao = daoFactory.getDepartmentDao(con);
            Department department = new Department();
            department.setId(0);

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.update(department);
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
            GenericDao<Department> dao = daoFactory.getDepartmentDao(con);
            Department department = new Department();
            department.setId(1);

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.delete(department);
            con.rollback(svpt);
            con.setAutoCommit(true);
        }
        Assert.assertTrue(res);
    }
}
