package uz.gita.findoneword.presenter;

import android.content.Context;

import java.util.ArrayList;

import uz.gita.findoneword.contract.GameContract;
import uz.gita.findoneword.data.QuestionData;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.Model model;
    private GameContract.View view;
    private GameContract.Model.LocalStorage storage;
    private int level;
    private ArrayList<String> writtenAnswers = new ArrayList<>(GameContract.MAX_ANSWER_COUNT);
    private ArrayList<Boolean> writtenVariant;

    public GamePresenter(Context context, GameContract.Model model, GameContract.View view) {
        storage = new GameContract.Model.LocalStorage(context);
        this.model = model;
        this.view = view;
        loadQuestion();
    }

    private void loadQuestion() {
        view.setQuestionNumber(("Level " + (level + 1)));
        QuestionData data = model.getQuestionByLevel(level);
        String answer = data.getAnswer();
        String variant = data.getVariant();
        view.loadQuestion(data.getQuestion1(), data.getQuestion2(), data.getQuestion3(), data.getQuestion4());
        writtenAnswers.clear();
        for (int i = 0; i < GameContract.MAX_ANSWER_COUNT; i++) {
            boolean state = i < answer.length();
            view.setStateAnswer(i, state);
            view.clearAnswer(i);
            writtenAnswers.add(null);
        }
        writtenVariant = new ArrayList<>(GameContract.MAX_VARIANT_COUNT);
        for (int i = 0; i < GameContract.MAX_VARIANT_COUNT; i++) {
            view.setVariant(i, String.valueOf(variant.charAt(i)));
            view.showVariant(i);
            writtenVariant.add(true);
        }
    }

    private int getEmptyAnswer() {
        for (int i = 0; i < writtenAnswers.size(); i++) {
            if (writtenAnswers.get(i) == null) return i;
        }
        return -1;
    }

    private int getVariantIndexByAnswer(int index) {
        String text = writtenAnswers.get(index);
        QuestionData data = model.getQuestionByLevel(level);
        String variants = data.getVariant();
        for (int i = 0; i < variants.length(); i++) {
            String variant = String.valueOf(variants.charAt(i));
            if (!writtenVariant.get(i) && variant.equals(text)) return i;
        }
        return -1;
    }

    @Override
    public void clickAnswer(int index) {
        view.clearAnswer(index);
        int variantIndex = getVariantIndexByAnswer(index);
        if (variantIndex == -1) return;
        view.showVariant(variantIndex);
        writtenAnswers.set(index, null);
        writtenVariant.set(variantIndex, true);
    }

    @Override
    public void clickVariant(int index) {
        int answerIndex = getEmptyAnswer();
        if (answerIndex == -1) return;
        QuestionData data = model.getQuestionByLevel(level);
        String text = String.valueOf(data.getVariant().charAt(index));
        view.writeAnswer(answerIndex, text);
        view.hideVariant(index);
        writtenAnswers.set(answerIndex, text);
        writtenVariant.set(index, false);
        checkWinner();
    }

    private void checkWinner() {
        QuestionData data = model.getQuestionByLevel(level);
        String originalAnswer = data.getAnswer();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < writtenAnswers.size(); i++) {
            String t = writtenAnswers.get(i);
            if (t != null) {
                builder.append(t);
            }
        }
        String userAnswer = builder.toString();
        if (userAnswer.length() != originalAnswer.length()) return;
        if (userAnswer.equals(originalAnswer)) {
            view.correctAnswer();
            level++;
            if (level == model.getQuestionCount()) {
                view.finishGame();
                return;
            }
            loadQuestion();
            storage.setCoins(Integer.toString(Integer.parseInt(storage.getCoins()) + 5));
            view.setCoins(storage.getCoins());
        } else {
            view.wrongAnswer();
//            storage.setCoins(Integer.toString(Integer.parseInt(storage.getCoins()) - 10));
            view.setCoins(storage.getCoins());
        }
    }
}
