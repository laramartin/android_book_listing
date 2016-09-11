package eu.laramartin.booklisting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Book> books = QueryUtils.extractBooks(QueryUtils.SAMPLE_JSON);

        BooksAdapter adapter = new BooksAdapter(this, -1);
        adapter.addAll(books);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
