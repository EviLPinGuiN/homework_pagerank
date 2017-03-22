package task.pagerank;

import task.pagerank.main.CrawlVogella;
import task.pagerank.model.Link;
import task.pagerank.model.Webpage;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nail on 16.03.2017.
 */
public class PageLinkExtractor {

    private Pattern htmltag = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>");

    private Pattern link = Pattern.compile("href=\"[^\"]*");

    public Set<Link> extractAllLinks(String webpageContent, Webpage page) {
        Set<Link> links = new HashSet<Link>();
        Matcher tagmatch = htmltag.matcher(webpageContent);
        while (tagmatch.find()) {
            Matcher matcher = link.matcher(tagmatch.group());
            matcher.find();
            String linkText = matcher.group().replaceFirst("href=\"", "")
                    .replaceFirst("\"", "");
            if (valid(linkText) && inside(linkText)) {
                links.add(new Link(page.getUrl(), linkText));
            }

        }

        return links;
    }

    /**
     * Link is not javascript or mailto or |.+search\?searchid.* or |.+new_print\\?p_cid.*
     **/
    private boolean valid(String s) {
        if (s.matches("javascript:.*|mailto:.*|#.*|.+\\.(pdf|docx|doc)" +
                "|.+search\\?searchid.*|.+new_print\\?p_cid.*|.+content_print\\?p_cid.*" +
                "|.+main_page\\?p_sub.*")) {
            return false;
        }
        return true;
    }

    private boolean inside(String s) {

        if (s.matches(CrawlVogella.CRAWL_START_PAGE + ".*")) {
            return true;
        }

        return false;
    }


}
