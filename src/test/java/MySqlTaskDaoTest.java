import com.boast.dao.DaoFactory;
import com.boast.dao.GenericDao;
import com.boast.dao.MySqlDaoFactory;
import com.boast.transferobject.Task;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.List;

public class MySqlTaskDaoTest {

    @Test
    public void testGetAll() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        List<Task> list;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Task> dao = daoFactory.getTaskDao(con);
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
        Task task;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Task> dao = daoFactory.getTaskDao(con);
            task = dao.getById(0);
        }
        Assert.assertNotNull(task);
    }

    @Test
    public void testCreate() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory();
        boolean res;
        try (Connection con = daoFactory.getConnection()) {
            GenericDao<Task> dao = daoFactory.getTaskDao(con);
            Task task = new Task();
            task.setId(100);
            task.setDescription("Test name");

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.create(task);
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
            GenericDao<Task> dao = daoFactory.getTaskDao(con);
            Task task = new Task();
            task.setId(0);

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.update(task);
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
            GenericDao<Task> dao = daoFactory.getTaskDao(con);
            Task task = new Task();
            task.setId(0);

            con.setAutoCommit(false);
            Savepoint svpt = con.setSavepoint("NewEmp");
            res = dao.delete(task);
            con.rollback(svpt);
            con.setAutoCommit(true);
        }
        Assert.assertTrue(res);
    }
}
