package repositories;

import com.google.common.collect.Lists;
import models.TodoItem;
import play.db.Database;
import repositories.exception.DatabaseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Singleton
public class TodoItemRepositoryImpl implements TodoItemRepository {

    @Inject
    private Database database;


    @Override
    public void save(TodoItem item) throws DatabaseException {
        Connection c = database.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("insert into todo (descricao, pronto) values(?, ?)");
            ps.setString(1, item.getDescricao());
            ps.setBoolean(2, item.getPronto());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            closeDatabaseConnection(c);
        }
    }



    @Override
    public void update(TodoItem item) throws DatabaseException {
        Connection c = database.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("update todo set descricao = ?, pronto = ? where id = ?");
            ps.setString(1, item.getDescricao());
            ps.setBoolean(2, item.getPronto());
            ps.setInt(3, item.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            closeDatabaseConnection(c);
        }
    }

    @Override
    public void delete(Integer id) throws DatabaseException {
        Connection c = database.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("delete from todo where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            closeDatabaseConnection(c);
        }
    }

    @Override
    public List<TodoItem> all() throws DatabaseException {
        List<TodoItem> itens = Lists.newArrayList();
        Connection c = database.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("select * from todo order by id");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                itens.add(buildTodoItem(resultSet));
            }
            return itens;

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            closeDatabaseConnection(c);
        }
    }

    @Override
    public Optional<TodoItem> one(Integer id) throws DatabaseException {
        Connection c = database.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("select * from todo where id = ?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            return (resultSet.next()) ? Optional.of(buildTodoItem(resultSet)) : Optional.empty();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            closeDatabaseConnection(c);
        }
    }

    private void closeDatabaseConnection(Connection c) {
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TodoItem buildTodoItem(ResultSet resultSet) throws SQLException {
        return new TodoItem(resultSet.getInt("id"),
                resultSet.getString("descricao"),
                resultSet.getBoolean("pronto"));
    }
}
