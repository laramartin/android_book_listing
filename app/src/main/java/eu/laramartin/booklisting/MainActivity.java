package eu.laramartin.booklisting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Book> books = QueryUtils.extractBooks();

//        List<String> authors = new ArrayList<>() ;
//        authors.add("Donn Felker");
//        authors.add("Second Author");
//        authors.add("Third Author");
//
//        String sampleAuthors = QueryUtils.formatListOfAuthors(authors);
//
//        Log.v("mainActivity", "sample list of authors: " + sampleAuthors);

    }


}
