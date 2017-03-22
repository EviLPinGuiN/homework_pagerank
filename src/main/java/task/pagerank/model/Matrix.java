package task.pagerank.model;

import task.pagerank.PageCrawler;
import task.pagerank.PageLinkExtractor;
import task.pagerank.links.LinksUtil;
import task.pagerank.model.sparsematrix.SparseMatrix;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

/**
 * Created by Nail on 15.03.2017.
 */
public class Matrix {

    private final static int MATRIX_SIZE = 100;


    private double[][] matrix;

    private SparseMatrix sparseMatrix;


    public Matrix() {
        matrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = 0.0;
            }
        }
    }


    public void extractLinks(CrawlerResult webpages,
                             PageCrawler crawler, Webpage page) {
        if (page != null) {
            String pageContent;
            try {
                pageContent = crawler.getPage(page);
                PageLinkExtractor extractedLinks = new PageLinkExtractor();
                Collection<Link> links = extractedLinks.extractAllLinks(
                        pageContent, page);
                System.out.println(rightPad("Crawled " + page.getUrl() + "\t",
                        80)
                        + " Found " + links.size() + " Links");
                for (Link link : links) {
                    Link absoluteLink = LinksUtil.createAbsoluteLink(link);
                    Webpage foundWebpage = new Webpage(absoluteLink.getLink());
                    webpages.add(foundWebpage);
                    page.getOutboundLinks().add(absoluteLink);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                webpages.getError().add(
                        new WebpageProblem(page, CrawlError.NOT_FOUND));
            }

        }
    }

    public void prepareResult(CrawlerResult webpages, boolean b) {
        for (WebpageProblem problem : webpages.getError()) {
            System.out.println(problem.getError() + " " + problem.getPage());
        }
        for (Webpage page : webpages.getWebpages()) {
            if (page.isCrawled()) {

                if (page.getOutboundLinks().size() <= 0) {
                    System.out.println(rightPad(page.getUrl(), 80)
                            + " No outbound links");
                }
            }
        }
        for (Webpage page : webpages.getWebpages()) {
            for (Link link : page.getOutboundLinks()) {
                for (Webpage inboundPage : webpages.getWebpages()) {
                    if (inboundPage.getUrl().equals(link.getLink())) {
                        inboundPage.getInboundLinks().add(link);

                    }
                }
            }
        }

        System.out.println("Number of links " + webpages.getWebpages().size());

        if (b) {
            /** 1 **/
            setSimpleMatrix(webpages);
        } else {
            /** 3 **/
            setSparseMatrix(webpages);
        }

    }

    public String rightPad(String s, int width) {
        return String.format("%-" + width + "s", s);
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public SparseMatrix getSparseMatrix() {
        return sparseMatrix;
    }

    public void setSparseMatrix(SparseMatrix sparseMatrix) {
        this.sparseMatrix = sparseMatrix;
    }


    private void setSimpleMatrix(CrawlerResult webpages) {
        for (int i = 0; i < webpages.getWebpages().size() && i < 100; i++) {
            Webpage page = webpages.getWebpages().get(i);
            for (int l = 0; l < page.getOutboundLinks().size(); l++) {
                Link link = page.getOutboundLinks().get(l);
                for (int j = 0; j < webpages.getWebpages().size() && j < 100; j++) {
                    if (i == j) {
                        continue;
                    }

                    Webpage inboundPage = webpages.getWebpages().get(j);
                    String linkUrl = link.getLink();
                    String inboundPageUrl = inboundPage.getUrl();
                    boolean temp = linkUrl.equals(inboundPageUrl);
                    if (temp) {
                        matrix[i][j] = 1.0;
                        continue;
                    }
                }
            }
        }
        System.out.print(" //**// ");
        for (int i = 0; i < MATRIX_SIZE; i++){
            System.out.print(webpages.getWebpages().get(i)  +" {");
        }
        System.out.println("},");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            System.out.print(webpages.getWebpages().get(i)  +" {");
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (j != MATRIX_SIZE - 1)
                    System.out.print(matrix[i][j] + ", ");
                else
                    System.out.print(matrix[i][j]);
            }
            System.out.println("},");
        }

    }


    private void setSparseMatrix(CrawlerResult webpages) {

        sparseMatrix = new SparseMatrix(MATRIX_SIZE, MATRIX_SIZE);

        for (int i = 0; i < webpages.getWebpages().size() && i < 100; i++) {
            Webpage page = webpages.getWebpages().get(i);
            for (int l = 0; l < page.getOutboundLinks().size(); l++) {
                Link link = page.getOutboundLinks().get(l);
                for (int j = 0; j < webpages.getWebpages().size() && j < 100; j++) {
                    if (i == j) {
                        continue;
                    }
                    Webpage inboundPage = webpages.getWebpages().get(j);
                    String linkUrl = link.getLink();
                    String inboundPageUrl = inboundPage.getUrl();
                    boolean temp = linkUrl.equals(inboundPageUrl);
                    if (temp) {
                        sparseMatrix.setQuick(i, j, 1.0);
                        continue;
                    }
                }
            }
        }

        sparseMatrix.print();
    }
}
