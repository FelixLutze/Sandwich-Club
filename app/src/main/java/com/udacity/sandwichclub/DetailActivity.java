package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import static com.squareup.picasso.Picasso.*;

public class DetailActivity extends AppCompatActivity {
    ImageView ingredientsIv;
    TextView alsoKnownAsTv;
    TextView ingredientsTv;
    TextView originTv;
    TextView descriptionTv;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.d("DEBUG-LOG", "Intent == null");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d("DEBUG-LOG", "EXTRA_POSITION not found in intent");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            Log.d("DEBUG-LOG", "Sandwich data unavailable");
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if(sandwich.getAlsoKnownAs().isEmpty()) {
            findViewById(R.id.also_known_tv_title).setVisibility(View.GONE);
            alsoKnownAsTv.setVisibility(View.GONE);
        }

        if(sandwich.getPlaceOfOrigin().isEmpty()) {
            findViewById(R.id.origin_tv_title).setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        }

        setTitle(sandwich.getMainName());
        with(this).load(sandwich.getImage()).into(ingredientsIv);
        alsoKnownAsTv.setText(String.join(", ", sandwich.getAlsoKnownAs()));
        ingredientsTv.setText(String.join(", ", sandwich.getIngredients()));
        originTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());
    }
}
