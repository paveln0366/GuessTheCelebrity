package com.pavelpotapov.guessthecelebrity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pavelpotapov.guessthecelebrity.di.AppComponent;
import com.pavelpotapov.guessthecelebrity.di.AppModule;
import com.pavelpotapov.guessthecelebrity.di.DaggerAppComponent;
import com.pavelpotapov.guessthecelebrity.di.PresenterModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements Contract.View {

    private ImageView imageViewPhoto;
    private ArrayList<TextView> buttons;

    // private Contract.Presenter mPresenter;
    @Inject
    Contract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // App.appComponent.inject(this);
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .presenterModule(new PresenterModule(this))
                .build();

        TextView button0 = findViewById(R.id.button0);
        TextView button1 = findViewById(R.id.button1);
        TextView button2 = findViewById(R.id.button2);
        TextView button3 = findViewById(R.id.button3);
        buttons = new ArrayList<>();
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);

        imageViewPhoto = findViewById(R.id.imageViewPhoto);

        mPresenter = new Presenter(this);
        mPresenter.startGame();
    }

    @Override
    public void showQuestion(Map<Bitmap, List<String>> question) {
        Bitmap photo = null;
        List<String> variantOfAnswers = new ArrayList<>();
        for (Bitmap key : question.keySet()) {
            photo = key;
            variantOfAnswers = question.get(key);
        }
        imageViewPhoto.setImageBitmap(photo);
        for (int i = 0; i < buttons.size(); i++) {
            TextView button = buttons.get(i);
            button.setText(variantOfAnswers.get(i));
        }
    }

    @Override
    public void showAnswer(Map<Boolean, String> answer) {
        if (answer.containsKey(true)) {
            Toast.makeText(this, R.string.answer_right, Toast.LENGTH_SHORT).show();
        } else if (answer.containsKey(false)) {
            Toast.makeText(this, getString(R.string.answer_wrong) + " " + answer.get(false), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    public void onClickAnswer(View button) {
        String tag = button.getTag().toString();
        mPresenter.onClickAnswer(tag);
    }

}