package repositories;

import com.google.inject.ImplementedBy;
import models.TodoItem;
import repositories.exception.DatabaseException;

import java.util.List;
import java.util.Optional;

@ImplementedBy(TodoItemRepositoryImpl.class)
public interface TodoItemRepository {
    void save(TodoItem item) throws DatabaseException;
    void update(TodoItem item) throws DatabaseException;
    void delete(Integer id) throws DatabaseException;
    List<TodoItem> all() throws DatabaseException;
    Optional<TodoItem> one(Integer id) throws DatabaseException;
}