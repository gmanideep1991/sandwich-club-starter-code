package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
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
        TextView also_known_as_tv = findViewById(R.id.also_known_tv);
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView place_of_origin_tv = findViewById(R.id.origin_tv);
        TextView description_tv = findViewById(R.id.description_tv);


        setTitle(sandwich.getMainName());
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        for (String alias:sandwich.getAlsoKnownAs()) {
            also_known_as_tv.append("- "+alias + "\n");
        }
        for(String ingredient:sandwich.getIngredients()){
            ingredients_tv.append("- " + ingredient+"\n");
        }
        description_tv.setText(sandwich.getDescription());
        place_of_origin_tv.setText(sandwich.getPlaceOfOrigin());
    }
}
