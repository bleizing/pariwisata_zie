package bleizing.pariwisata;

/**
 * Created by Bleizing on 3/28/2018.
 */

public class Model {
    private static boolean chooseRecommendation = false;

    private static int questionOne = 0;
    private static int questionTwo = 0;
    private static int questionThree = 0;

    private static String recommendation = "";

    public static void setChooseRecommendation(boolean chooseRecommendation) {
        Model.chooseRecommendation = chooseRecommendation;
    }

    public static boolean isChooseRecommendation() {
        return chooseRecommendation;
    }

    public static void setQuestionOne(int questionOne) {
        Model.questionOne = questionOne;
    }

    public static int getQuestionOne() {
        return questionOne;
    }

    public static void setQuestionTwo(int questionTwo) {
        Model.questionTwo = questionTwo;
    }

    public static int getQuestionTwo() {
        return questionTwo;
    }

    public static void setQuestionThree(int questionThree) {
        Model.questionThree = questionThree;
    }

    public static int getQuestionThree() {
        return questionThree;
    }

    public static void setRecommendation(String recommendation) {
        Model.recommendation = recommendation;
    }

    public static String getRecommendation() {
        return recommendation;
    }
}
