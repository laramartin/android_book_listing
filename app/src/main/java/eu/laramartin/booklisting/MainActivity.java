package eu.laramartin.booklisting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
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

//    private class AsyncTask extends android.os.AsyncTask<URL, Void, Book> {
//
//        @Override
//        protected Book doInBackground(URL... urls) {
//            return null;
//        }
//    }

    private URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("mainActivity", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private List<Book> parseJson(String json) {

        if (json == null) {
            return null;
        }

        List<Book> books =  QueryUtils.extractBooks(json);
        return books;
    }

}
