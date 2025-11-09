import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;
    private Scene loginScene;
    private Scene mainMenuScene;
    private Scene wordLessonScene;
    private Scene grammarTestScene;

    private String currentUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Language Learning App");

        primaryStage.setWidth(1200);
        primaryStage.setHeight(900);

        createLoginScene();
        createMainMenuScene();
        createWordLessonScene();
        createGrammarTestScene();

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void createLoginScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER); // Центрируем все элементы в VBox

        Label titleLabel = new Label("Language Learning App");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setPrefWidth(200);
        signUpButton.setOnAction(e -> showSignUpMode());

        Button loginButton = new Button("Log In");
        loginButton.setPrefWidth(200);
        loginButton.setOnAction(e -> showLoginMode());

        root.getChildren().addAll(titleLabel, signUpButton, loginButton);

        loginScene = new Scene(root, 300, 250); // Размер сцены
    }

    private void showSignUpMode() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert("Error", "Please enter both username and password.");
                return;
            }

            // Регистрируем нового пользователя
            UserDatabase userDatabase = new UserDatabase();
            userDatabase.registerUser(usernameField.getText(), passwordField.getText());
            currentUser = usernameField.getText();
            primaryStage.setScene(mainMenuScene); // Переход на главное меню
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> primaryStage.setScene(loginScene)); // Возвращаемся к начальному экрану

        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, signUpButton, cancelButton);

        // Мы не меняем loginScene, а устанавливаем новую сцену для регистрации
        Scene signUpScene = new Scene(root, 300, 250);
        primaryStage.setScene(signUpScene);
    }

    private void showLoginMode() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginButton = new Button("Log In");
        loginButton.setOnAction(e -> {
            // Логика входа
            UserDatabase userDatabase = new UserDatabase();
            if (userDatabase.loginUser(usernameField.getText(), passwordField.getText())) {
                currentUser = usernameField.getText();
                primaryStage.setScene(mainMenuScene); // Переход на главное меню
            } else {
                showAlert("Error", "Invalid username or password.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> primaryStage.setScene(loginScene)); // Возвращаемся к начальному экрану

        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, cancelButton);

        // Мы не меняем loginScene, а устанавливаем новую сцену для входа
        Scene loginModeScene = new Scene(root, 300, 250);
        primaryStage.setScene(loginModeScene);
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    private void createMainMenuScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Label welcomeLabel = new Label("Welcome, " + (currentUser != null ? currentUser : "Guest"));

        Button studyWordsButton = new Button("Study New Words");
        studyWordsButton.setPrefWidth(200);
        studyWordsButton.setOnAction(e -> primaryStage.setScene(wordLessonScene));

        Button grammarTestsButton = new Button("Grammar Tests");
        grammarTestsButton.setPrefWidth(200);
        grammarTestsButton.setOnAction(e -> primaryStage.setScene(grammarTestScene));

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(e -> primaryStage.close());

        Button backButton = new Button("Back to Login");
        backButton.setPrefWidth(200);
        backButton.setOnAction(e -> primaryStage.setScene(loginScene));

        root.getChildren().addAll(welcomeLabel, studyWordsButton, grammarTestsButton, exitButton, backButton);

        mainMenuScene = new Scene(root, 300, 300);
    }

    private void createWordLessonScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Label questionLabel = new Label("¿Hola?");
        Label answerLabel = new Label("Hello");

        ToggleGroup answerGroup = new ToggleGroup();

        RadioButton option1 = new RadioButton("Hello");
        RadioButton option2 = new RadioButton("Goodbye");
        RadioButton option3 = new RadioButton("Thank you");
        RadioButton option4 = new RadioButton("Yes");

        option1.setToggleGroup(answerGroup);
        option2.setToggleGroup(answerGroup);
        option3.setToggleGroup(answerGroup);
        option4.setToggleGroup(answerGroup);

        Button submitButton = new Button("Submit");
        Label resultLabel = new Label();

        submitButton.setOnAction(e -> {
            if (option1.isSelected()) {
                resultLabel.setText("Excellent!");
            } else {
                resultLabel.setText("Try again!");
            }
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(questionLabel, option1, option2, option3, option4, submitButton, resultLabel, backButton);

        wordLessonScene = new Scene(root, 400, 300);
    }

    private void createGrammarTestScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Label questionLabel = new Label("The cat ___ on the mat.");
        Label answerLabel = new Label("is");

        ToggleGroup answerGroup = new ToggleGroup();

        RadioButton option1 = new RadioButton("is");
        RadioButton option2 = new RadioButton("are");
        RadioButton option3 = new RadioButton("am");
        RadioButton option4 = new RadioButton("be");

        option1.setToggleGroup(answerGroup);
        option2.setToggleGroup(answerGroup);
        option3.setToggleGroup(answerGroup);
        option4.setToggleGroup(answerGroup);

        Button submitButton = new Button("Submit");
        Label resultLabel = new Label();

        submitButton.setOnAction(e -> {
            if (option1.isSelected()) {
                resultLabel.setText("Excellent!");
            } else {
                resultLabel.setText("Try again!");
            }
        });

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));

        root.getChildren().addAll(questionLabel, option1, option2, option3, option4, submitButton, resultLabel, backButton);

        grammarTestScene = new Scene(root, 400, 300);
    }
}