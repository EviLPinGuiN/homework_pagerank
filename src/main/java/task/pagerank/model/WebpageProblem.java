package task.pagerank.model;

/**
 * Created by Nail on 14.03.2017.
 */
public class WebpageProblem {

	private final Webpage page;
	private final CrawlError error;
	
	public WebpageProblem(Webpage page, CrawlError error ) {
		this.page = page;
		this.error = error;
	}

	public Webpage getPage() {
		return page;
	}

	public CrawlError getError() {
		return error;
	}
	
}
