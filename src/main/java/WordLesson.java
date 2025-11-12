import javafx.scene.image.Image;

public class WordLesson {
    private String word;
    private String translation;
    private String imagePath; // Теперь хранится путь к изображению
    private String[] options;
    private String correctAnswer;
    private int id;

    public WordLesson(String word, String translation, String imagePath, String[] options, String correctAnswer) {
        this.word = word;
        this.translation = translation;
        this.imagePath = imagePath;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // ✅ Удалён метод getImageBytes()

    public Image getImage() {
        return new Image("file:" + imagePath); // Загрузка изображения по пути
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }
}
