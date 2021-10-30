package uz.gita.findoneword.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import uz.gita.findoneword.R;
import uz.gita.findoneword.contract.GameContract;
import uz.gita.findoneword.presenter.GamePresenter;
import uz.gita.findoneword.repository.GameRepository;

public class MainActivity extends AppCompatActivity implements GameContract.View {
    private ImageView questionImageView1;
    private ImageView questionImageView2;
    private ImageView questionImageView3;
    private ImageView questionImageView4;
    private ArrayList<Button> answers;
    private ArrayList<Button> variants;
    private GameContract.Presenter presenter;
    private TextView coins;
    private TextView questionNumber;
    private GameContract.Model.LocalStorage storage;
    private Object gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gameType = bundle.get("GAME_TYPE");
            if (gameType == GameRepository.class) {
                presenter = new GamePresenter(this, new GameRepository(), this);
            }
        }

        findViewById(R.id.back_option).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            findViewById(R.id.winner_screen).setVisibility(View.GONE);
            finish();
        });

        findViewById(R.id.btnRestart).setOnClickListener(v -> {
            findViewById(R.id.winner_screen).setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("GAME_TYPE", GameRepository.class);
            startActivity(intent);
        });
    }

    private void loadViews() {
        storage = new GameContract.Model.LocalStorage(this);
        coins = findViewById(R.id.coins);
        questionNumber = findViewById(R.id.questionNumber);
        questionImageView1 = findViewById(R.id.imageQuestion1);
        questionImageView2 = findViewById(R.id.imageQuestion2);
        questionImageView3 = findViewById(R.id.imageQuestion3);
        questionImageView4 = findViewById(R.id.imageQuestion4);
        answers = getAllByGroup(0, R.id.containerAnswer, v -> presenter.clickAnswer((int) v.getTag()));
        variants = getAllByGroup(0, R.id.containerVariant1, this::clickVariant);
        variants.addAll(getAllByGroup(variants.size(), R.id.containerVariant2, this::clickVariant));
    }

    private ArrayList<Button> getAllByGroup(int startIndex, int groupId, View.
            OnClickListener onClickListener) {
        ViewGroup group = findViewById(groupId);
        ArrayList<Button> buttons = new ArrayList<>(group.getChildCount());
        for (int i = 0; i < group.getChildCount(); i++) {
            Button button = (Button) group.getChildAt(i);
            button.setOnClickListener(onClickListener);
            button.setTag(i + startIndex);
            buttons.add(button);
        }
        return buttons;
    }


    private void clickVariant(View view) {
        int index = (int) view.getTag();
        presenter.clickVariant(index);
    }

    @Override
    public void setCoins(String coin) {
        coins.setText(coin);
    }

    @Override
    public void setQuestionNumber(String questionNumber) {
        this.questionNumber.setText(questionNumber);
    }

    @Override
    public void hideVariant(int index) {
        variants.get(index).setClickable(false);
        variants.get(index).setBackgroundResource(R.drawable.hidden_variant_bg);
    }

    @Override
    public void showVariant(int index) {
        variants.get(index).setClickable(true);
        variants.get(index).setVisibility(View.VISIBLE);
        variants.get(index).setBackgroundResource(R.drawable.variant_letter_background);
    }

    @Override
    public void writeAnswer(int index, String text) {
        answers.get(index).setText(text);
    }

    @Override
    public void clearAnswer(int index) {
        writeAnswer(index, "");
    }

    @Override
    public void setStateAnswer(int index, boolean state) {
        answers.get(index).setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setVariant(int index, String text) {
        variants.get(index).setText(text);
    }

    @Override
    public void loadQuestion(String imageName1, String imageName2, String imageName3, String imageName4) {
        int id1 = getResources().getIdentifier(imageName1, "drawable", getPackageName());
        questionImageView1.setImageResource(id1);

        int id2 = getResources().getIdentifier(imageName2, "drawable", getPackageName());
        questionImageView2.setImageResource(id2);

        int id3 = getResources().getIdentifier(imageName3, "drawable", getPackageName());
        questionImageView3.setImageResource(id3);

        int id4 = getResources().getIdentifier(imageName4, "drawable", getPackageName());
        questionImageView4.setImageResource(id4);
    }

    @Override
    public void wrongAnswer() {
        Toast.makeText(this, "wrongAnswer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void correctAnswer() {
//        Toast.makeText(this, "correctAnswer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishGame() {
        findViewById(R.id.winner_screen).setVisibility(View.VISIBLE);
        Toast.makeText(this, "finishGame", Toast.LENGTH_SHORT).show();
    }
}