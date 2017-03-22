package task.pagerank.links;

import task.pagerank.model.Webpage;

import java.util.Comparator;



/**
 * Created by Nail on 14.03.2017.
 */
public class LinkComparator implements Comparator<Webpage>{

	@Override
	public int compare(Webpage o1, Webpage o2) {
		if (o1.getInboundLinks().size() >o2.getInboundLinks().size()){
			return -1; 
		} 
		if (o1.getInboundLinks().size() < o2.getInboundLinks().size()){
			return 1; 
		} 
		return 0;
	}
}
