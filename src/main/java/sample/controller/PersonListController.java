package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.gui.modeladapter.ListViewModelAdapter;
import sample.gui.WindowManager;
import sample.model.Person;
import sample.database.PersonService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonListController {

    @FXML
    private ListView<Person> personsListView;

    private ObservableList<Person> personObservableList;

    @Inject
    private PersonService personService;

    @Inject WindowManager windowManager;

    @FXML
    private void initialize() {
        System.out.println("initialize");
        personsListView.setCellFactory((ListView<Person> param) -> new ListViewModelAdapter<>());
        setupBindings();
        setupListeners();
    }

    private void setupBindings() {
        System.out.println("setupBindings");
        personObservableList = FXCollections.observableArrayList( Person.extractor() );
        personObservableList.addAll(personService.getAll());
        personsListView.setItems( personObservableList );
    }

    private void setupListeners() {
        System.out.println("setupListeners");
        personsListView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    windowManager.switchScene(WindowManager.SCENES.PERSON_EDIT_SCENE);
                }
            }
        });
    }

    public Person getPersonSelected() {
        return personsListView.getSelectionModel().getSelectedItem();
    }

}
