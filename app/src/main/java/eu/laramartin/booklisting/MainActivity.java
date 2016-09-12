package eu.laramartin.booklisting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        List<Book> books = QueryUtils.extractBooks(QueryUtils.SAMPLE_JSON);

        BooksAdapter adapter = new BooksAdapter(this, -1);
        adapter.addAll(books);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        imageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrlForHttpRequest();
            }
        });

    }

    private String getUserInput() {
        return editText.getText().toString();
    }

    private String getUrlForHttpRequest() {
        final String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=search+";

        String formatUserInput = getUserInput().trim().replaceAll("\\s+","+");

        String url = baseUrl + formatUserInput;
        Log.v("mainactivity", "user input: " + formatUserInput);
        Log.v("mainactivity", "url: " + url);

        return url;
    }
}
