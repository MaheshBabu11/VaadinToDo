package com.example.application.views;

import com.example.application.ToDo;
import com.example.application.ToDoRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("")
public class TodoView extends VerticalLayout {

    private ToDoRepository repo;

    public TodoView(ToDoRepository repo) {
        this.repo = repo;

        var task = new TextField();
        var button = new Button("Add");
        var todos = new VerticalLayout();

        todos.setPadding(false);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);
        button.addClickListener(click -> {
            var todo = repo.save(new ToDo(task.getValue()));
            todos.add(createCheckbox(todo));
            task.clear();
        });

        repo.findAll().forEach(todo -> todos.add(createCheckbox(todo)));

        add(
                new H1("Todo"),
                new HorizontalLayout(task, button),
                todos
        );
    }

    private Component createCheckbox(ToDo todo) {
        return new Checkbox(todo.getTask(), todo.isCompleted(), e -> {
            todo.setCompleted(e.getValue());
            repo.save(todo);
        });
    }
}
