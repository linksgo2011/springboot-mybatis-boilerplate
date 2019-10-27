package boilerplate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
class TodoControllerTest {
    @Autowired
    private TodoController todoController;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldGetAListIncludeAllTodos() throws Exception {
        //given
        when(todoRepository.getAll()).thenReturn(Arrays.asList(new Todo(1,"first todo",true,0)));
        //when
        ResultActions result = mvc.perform(get("/todos"));
        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].title", is("first todo")))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].completed", is(true)))
                .andExpect(jsonPath("$[0].order", is(0)));
    }

    @Test
    void shouldGetOneItemFromTodos() throws Exception {
        //given
        when(todoRepository.findById(1)).thenReturn(Optional.of(new Todo(1,"first todo",true,0)));
        //when
        ResultActions result = mvc.perform(get("/todos/1"));
        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title", is("first todo")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.completed", is(true)))
                .andExpect(jsonPath("$.order", is(0)));
    }

    @Test
    void shouldSaveOneItemToTodos() throws Exception {
        //given
        Todo  todo = new Todo(1,"first todo",true,0);
        //when
        ResultActions result = mvc.perform(post("/todos",todo)
                .content(mapper.writeValueAsString(todo))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.title", is("first todo")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.completed", is(true)))
                .andExpect(jsonPath("$.order", is(0)));
    }


    @Test
    void shouldDeleteItemFromTodosWhenItemIsExist() throws Exception {
        //given
        when(todoRepository.findById(1)).thenReturn(Optional.of(new Todo(1,"first todo",true,0)));
        //when
        ResultActions result = mvc.perform(delete("/todos/1"));

        //then
        result.andExpect(status().isOk());
    }

    @Test
    void shouldGot404ErrorForDeleteWhenItemIsNotExist() throws Exception {
        //given
        when(todoRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        //when
        ResultActions result = mvc.perform(delete("/todos/1"));
        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateOneItemInTodos() throws Exception {
        //given
        Todo  todo = new Todo(1,"first todo",true,0);
        when(todoRepository.findById(1)).thenReturn(Optional.of(new Todo(1,"first todo",true,0)));
        //when
        ResultActions result = mvc.perform(patch("/todos/{id}",1)
                .content(mapper.writeValueAsString(todo))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title", is("first todo")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.completed", is(true)))
                .andExpect(jsonPath("$.order", is(0)));

        verify(todoRepository).delete(any(Todo.class));
        verify(todoRepository).add(any(Todo.class));
    }


    @Test
    void shouldGot404ErrorForUpdateWhenItemIsNotExist() throws Exception {
        //given
        Todo  todo = new Todo(1,"first todo",true,0);
        //when
        ResultActions result = mvc.perform(patch("/todos/{id}",1)
                .content(mapper.writeValueAsString(todo))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    void shouldGot400ErrorForUpdateWhenNewItemIsEmpty() throws Exception {
        //when
        when(todoRepository.findById(1)).thenReturn(Optional.of(new Todo(1,"first todo",true,0)));

        //then
        ResultActions result = mvc.perform(patch("/todos/{id}",1)
                .content(mapper.writeValueAsString(null))
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isBadRequest()).andDo(print());
    }
}
