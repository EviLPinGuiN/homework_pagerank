package task.pagerank.main;

import task.pagerank.model.Matrix;
import task.pagerank.PageCrawler;
import task.pagerank.rank.*;
import task.pagerank.model.sparsematrix.SparseMatrix;
import task.pagerank.model.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


/**
 * Created by Nail on 13.03.2017.
 */
public class CrawlVogella {

    //    public final static String CRAWL_START_PAGE = "http://www.deviantart.com/";
    public final static String CRAWL_START_PAGE = "http://kpfu.ru/";
//    public final static String CRAWL_START_PAGE = "http://www.world-art.ru/";


    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        CrawlerResult webpages = CrawlerResult.INSTANCE;
        PrintWriter printWriter = new PrintWriter("result.txt");

        webpages.add(new Webpage(CRAWL_START_PAGE));


        PageCrawler crawler = new PageCrawler();
        Matrix matrix = new Matrix();
        /** 1 **/
        while (webpages.hasNext())
            try {
                Webpage page = webpages.next();
                matrix.extractLinks(webpages, crawler, page);
                page.setCrawled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        /**if simple matrix = true
         * if sparse matrix = false **/
        boolean b = true;


        /** 3 (iterative) **/
        calculate(webpages, matrix, new Iterative(), !b , printWriter);

        printWriter.println();
        printWriter.println();
        printWriter.println();

        /** 4 (MultiIterative)**/
        calculate(webpages, matrix, new MultiIterative(), !b , printWriter);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Runtime " + elapsedTime);

        printWriter.close();

    }



    private static void calculate(CrawlerResult webpages,  Matrix matrix, IIterative iIterative, boolean b, PrintWriter printWriter){


        matrix.prepareResult(webpages,b);
        double[] rank = iIterative.calculate(matrix.getSparseMatrix());

        Map<String, Double> result = new HashMap<>();
        IntStream.range(0, rank.length)
                .forEach(r -> result.put(webpages.getWebpages().get(r).getUrl(), rank[r]));
        result.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(e -> {
                    printWriter.print(e.getKey() + "\t");
                    printWriter.println(e.getValue());
                });
        printWriter.println("Runtime: " + iIterative.getTime() + " ms");
    }




}
