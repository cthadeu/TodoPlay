import com.google.common.collect.Lists;
import controllers.TodoItemController;
import controllers.routes;
import models.TodoItem;
import org.junit.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import play.Application;
import play.api.test.FakeApplication$;
import play.libs.Json;
import play.mvc.*;
import play.test.*;
import play.twirl.api.Content;
import repositories.TodoItemRepository;
import repositories.TodoItemRepositoryImpl;
import repositories.exception.DatabaseException;

import java.util.Optional;

import static com.google.common.collect.Lists.*;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.OK;
import static org.junit.Assert.*;
import static play.test.Helpers.*;


@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest extends WithApplication {

    public static FakeApplication$ app;

    private TodoItemRepository todoItemRepository;
    private static final TodoItem defaultItem = new TodoItem(1, "Test Item", false);

    @Before
    public void setUp() throws DatabaseException {
        todoItemRepository = mock(TodoItemRepository.class);
        when(todoItemRepository.all())
                .thenReturn(newArrayList(defaultItem));

        when(todoItemRepository.one(1)).thenReturn(Optional.of(defaultItem));
    }

    @Test
    public void test_should_get_200_when_request_index() throws Exception {
        Result result = new TodoItemController(todoItemRepository).index();
        assertEquals(OK, result.status());
        assertTrue(contentAsString(result).contains("Test Item"));
    }

    @Test
    public void test_should_get_ok_when_post_todo(){
        running(fakeApplication(), () -> {
            Call action = routes.TodoItemController.insert();
            Result result = route(fakeRequest(action)
                                    .method("POST")
                                    .bodyJson(Json.toJson(defaultItem)));
            assertEquals(OK, result.status());
        });
    }

    @Test
    public void test_should_get_bad_request_when_empty_post() {
        running(fakeApplication(), () -> {
            Call action = routes.TodoItemController.insert();
            Result result = route(fakeRequest(action));
            assertEquals(BAD_REQUEST, result.status());
        });
    }

    @Test
    public void test_should_get_ok_when_put_todo_item() {
        running(fakeApplication(), () -> {
            Call action = routes.TodoItemController.update();
            Result putResult = route(fakeRequest(action).method("PUT").bodyJson(Json.toJson(defaultItem)));
            assertEquals(OK, putResult.status());
        });
    }

    @Test
    public void test_should_get_ok_when_delete_todo_item() {
        running(fakeApplication(), () -> {
            Call action = routes.TodoItemController.delete(1);
            Result deleteResult = route(fakeRequest(action).method("DELETE"));
            assertEquals(OK, deleteResult.status());
        });
    }


}
