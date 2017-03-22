package task.pagerank;

import task.pagerank.model.Webpage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Nail on 15.03.2017.
 */
public class PageCrawler {

    public String getPage(Webpage page) throws IOException, MalformedURLException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new URL(page.getUrl()).openStream()));

        String s;
        StringBuilder builder = new StringBuilder();

        while ((s = bufferedReader.readLine()) != null) {
            builder.append(s);
            builder.append("\n");
        }
        bufferedReader.close();

        return builder.toString();
    }

}
