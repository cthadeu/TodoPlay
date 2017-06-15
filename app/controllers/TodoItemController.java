package controllers;

import java.util.Optional;
import javax.inject.Inject;

import models.TodoItem;
import play.mvc.*;
import repositories.TodoItemRepository;
import repositories.exception.DatabaseException;

import static play.libs.Json.*;

public class TodoItemController extends Controller {

    private TodoItemRepository todoItemRepository;

    @Inject
    public TodoItemController(TodoItemRepository repository)  {
        this.todoItemRepository = repository;
    }

    public Result index() throws Exception {
         return ok(toJson(todoItemRepository.all()));
    }
    
    public Result delete(int id) throws Exception {
        todoItemRepository.delete(id);
        return ok();
    }

    public Result insert() throws Exception {
        try {
            TodoItem todoItem = todoItemFromJsonBody();
            todoItemRepository.save(todoItem);
            return ok(toJson(todoItem));
        } catch (Exception e) {
            return badRequest();
        }
    }


    public Result update() throws Exception {
        try {
            TodoItem todoItem = todoItemFromJsonBody();
            todoItemRepository.update(todoItem);
            return ok(toJson(todoItem));
        } catch (Exception e) {
            return badRequest();
        }
    }

    public Result getById(int id) throws  Exception {
        Optional<TodoItem> itemOptional = todoItemRepository.one(id);
        return itemOptional.isPresent() ? ok(toJson(itemOptional.get()))
                                        : notFound();

    }

    private TodoItem todoItemFromJsonBody() {
        return fromJson(request().body().asJson(), TodoItem.class);
    }

}
