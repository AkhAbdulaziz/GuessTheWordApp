package uz.gita.findoneword.data;

public class QuestionData {
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String answer;
    private String variant;

    public QuestionData(String question1,String question2,String question3,String question4, String answer, String variant) {
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.answer = answer;
        this.variant = variant;
    }

    public String getQuestion1() {
        return question1;
    }
    public String getQuestion2() {
        return question2;
    }
    public String getQuestion3() {
        return question3;
    }
    public String getQuestion4() {
        return question4;
    }

    public String getAnswer() {
        return answer;
    }

    public String getVariant() {
        return variant;
    }
}
