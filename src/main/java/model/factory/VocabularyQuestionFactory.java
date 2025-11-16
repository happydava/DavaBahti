package model.factory;

import db.DatabaseConnection;
import model.questions.Question;
import model.builder.VocabularyQuestionBuilder;

import java.sql.*;
import java.util.List;

public class VocabularyQuestionFactory implements QuestionFactory {

    @Override
    public Question createQuestion(int index) {
        String query = "SELECT * FROM vocabulary_questions LIMIT 1 OFFSET " + index;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new VocabularyQuestionBuilder()
                        .setQuestionText(rs.getString("word"))
                        .setImagePath(rs.getString("image_path"))
                        .setOptions(List.of(
                                rs.getString("option1"),
                                rs.getString("option2"),
                                rs.getString("option3"),
                                rs.getString("option4")
                        ))
                        .setCorrectAnswer(rs.getString("correct_answer"))
                        .build();
            }

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
}
