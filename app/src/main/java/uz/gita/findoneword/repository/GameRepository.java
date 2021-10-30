package uz.gita.findoneword.repository;

import java.util.ArrayList;

import uz.gita.findoneword.contract.GameContract;
import uz.gita.findoneword.data.QuestionData;

public class GameRepository implements GameContract.Model {
    private ArrayList<QuestionData> data;

    public GameRepository() {
        data = new ArrayList<>();
        data.add(new QuestionData("strawberry", "banana", "kiwi", "cherry", "fruit", "itausbdnnagrfe"));
        data.add(new QuestionData("bird", "fish", "tiger", "tree", "living", "iflusbeanvgite"));
        data.add(new QuestionData("ant", "chain", "elephant", "hand", "strength", "ihtusbernvgite"));
        data.add(new QuestionData("climber", "daughter", "ladder", "plane", "trust", "euihnvgittrsbe"));
        data.add(new QuestionData("apple", "barbecue", "icecream", "nuts", "food", "ruofnzqidtrsom"));
        data.add(new QuestionData("canter_stadium", "center_cubic", "center_earth", "center_game", "center", "euonczqidtrsep"));
        data.add(new QuestionData("coconut", "hard_disc", "hardwork", "stone", "hard", "ahoncrqidtrdep"));
        data.add(new QuestionData("frog", "scorpion", "snake", "tsetse_fly", "toxic", "xhoicrqfdtryec"));
        data.add(new QuestionData("ants", "drop", "sand", "sweet", "tiny", "wnoicrqfdtryec"));
        data.add(new QuestionData("penguin", "cold_mountain", "coffee", "polor_bear", "cold", "dflcsbelnvgots"));
    }

    @Override
    public int getQuestionCount() {
        return data.size();
    }

    @Override
    public QuestionData getQuestionByLevel(int level) {
        return data.get(level);
    }
}
