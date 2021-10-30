package uz.gita.findoneword.contract;

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

import uz.gita.findoneword.data.QuestionData;

public interface GameContract {
    int MAX_ANSWER_COUNT = 10;
    int MAX_VARIANT_COUNT = 14;

    interface Model {

        class LocalStorage {
            private static String COINS = "coinsnkiwx3";

            private SharedPreferences preferences;

            public LocalStorage(Context context) {
                preferences = new SecurePreferences(context, "skjsjdiej4654ss", "LocalStorage");
            }

            public void setCoins(String coins) {
                preferences.edit().putString(COINS, coins).apply();
            }

            public String getCoins() {
                return preferences.getString(COINS, "100");
            }
        }

        int getQuestionCount();

        QuestionData getQuestionByLevel(int level);
    }

    interface View {
        void setCoins(String coins);

        void setQuestionNumber(String questionNumber);

        void hideVariant(int index);

        void showVariant(int index);

        void writeAnswer(int index, String text);

        void clearAnswer(int index);

        void setStateAnswer(int index, boolean state);

        void setVariant(int index, String text);

        void loadQuestion(String imageName1, String imageName2, String imageName3, String imageName4);

        void wrongAnswer();

        void correctAnswer();

        void finishGame();
    }

    interface Presenter {
        void clickAnswer(int index);

        void clickVariant(int index);
    }
}
