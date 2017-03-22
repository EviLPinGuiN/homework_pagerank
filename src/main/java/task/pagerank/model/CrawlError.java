package task.pagerank.model;


/**
 * Created by Nail on 14.03.2017.
 */
public enum CrawlError {
    NOT_FOUND("HTML return code 404 - Resource not found");

    private String errorText;

    CrawlError(String s) {
        errorText = s;
    }

    @Override
    public String toString() {
        return errorText;
    }
}
