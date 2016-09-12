package eu.laramartin.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lara on 11/09/2016.
 */
public class QueryUtils {

    // hardcoded results of query https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10
    // for testing
//    static final String SAMPLE_JSON = "{\"kind\":\"books#volumes\",\"totalItems\":500,\"items\":["+
//            "{\"kind\":\"books#volume\",\"id\":\"KPjmuogFmU0C\",\"etag\":\"khFZy+4GIIc\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/KPjmuogFmU0C\",\"volumeInfo\":{\"title\":\"AndroidAppsEntwicklungfürDummies\",\"authors\":[\"DonnFelker\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"yFnOZwlFe7QC\",\"etag\":\"CohSOo1Y57k\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/yFnOZwlFe7QC\",\"volumeInfo\":{\"title\":\"DasAndroidSmartphone\",\"subtitle\":\"10LektionenfürEinsteiger\",\"authors\":[\"RainerHattenhauer\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"34S3Jt1ONTkC\",\"etag\":\"KlPbkSignig\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/34S3Jt1ONTkC\",\"volumeInfo\":{\"title\":\"EinführungindieAndroid-Entwicklung\",\"authors\":[\"MarkoGargenta\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"h_3SAwAAQBAJ\",\"etag\":\"fT8Naou8TIE\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/h_3SAwAAQBAJ\",\"volumeInfo\":{\"title\":\"DasAndroid-Smartphone-Buch\",\"authors\":[\"HansDorsch\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"X3w3D_Dv2lMC\",\"etag\":\"H/ReM5feJ/8\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/X3w3D_Dv2lMC\",\"volumeInfo\":{\"title\":\"AndroidAppsMarketing\",\"subtitle\":\"SecretstoSellingYourAndroidApp\",\"authors\":[\"JeffreyHughes\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"TwV45aVC14IC\",\"etag\":\"W6HJdMjVKLU\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/TwV45aVC14IC\",\"volumeInfo\":{\"title\":\"AndroidProgrammingUnleashed\",\"authors\":[\"B.M.Harwani\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"IEk2m00o9_IC\",\"etag\":\"9pkK1vu9rkE\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/IEk2m00o9_IC\",\"volumeInfo\":{\"title\":\"AndroidAppsSecurity\",\"authors\":[\"SheranGunasekera\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"gSdFq2ype_sC\",\"etag\":\"ylHSSluSkw8\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/gSdFq2ype_sC\",\"volumeInfo\":{\"title\":\"AndroidAppsforAbsoluteBeginners\",\"authors\":[\"WallaceJackson\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"zDibrpXTfxMC\",\"etag\":\"M1xPD1MTABg\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/zDibrpXTfxMC\",\"volumeInfo\":{\"title\":\"AndroidForensics\",\"subtitle\":\"Investigation,Analysis,andMobileSecurityforGoogleAndroid\",\"authors\":[\"AndrewHoog\"]}},"+
//            "{\"kind\":\"books#volume\",\"id\":\"MoXpe6H2B5gC\",\"etag\":\"vUsJySLC9Co\",\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/MoXpe6H2B5gC\",\"volumeInfo\":{\"title\":\"AndroidinTheAttic\",\"authors\":[\"NicholasAllan\"]}}]}";
//

    private QueryUtils() {
    }

    public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {

        String authorsListInString = null;

        if (authorsList.length() == 0) {
            return null;
        }

        for (int i = 0; i < authorsList.length(); i++){
            if (i == 0) {
                authorsListInString = authorsList.getString(0);
            } else {
                authorsListInString += ", " + authorsList.getString(i);
            }
        }

        return authorsListInString;
    }


    public static List<Book> extractBooks(String json) {

        List<Book> books = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray jsonArray = jsonResponse.getJSONArray("items");

            Log.v("queryUtils", jsonArray.toString());
            Log.v("queryUtils", Integer.toString(jsonArray.length()));

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject bookObject = jsonArray.getJSONObject(i);
                Log.v("queryUtils", "item num " + i + ": " + bookObject);

                JSONObject bookInfo = bookObject.getJSONObject("volumeInfo");
                Log.v("queryUtils", "book info: " + bookInfo);

                String title = bookInfo.getString("title");
                JSONArray authorsArray = bookInfo.getJSONArray("authors");
                String authors = formatListOfAuthors(authorsArray);
                Log.v("queryUtils", "title: " + title);
                Log.v("queryUtils", "authors list: " + authors);

                Book book = new Book(authors, title);
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }



}
