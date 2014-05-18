package csns.model.site.dao;

import java.util.List;

import csns.model.site.Block;
import csns.model.site.Site;

public interface BlockDao {
	
	Block getBlockById( Long id );
	
	List<Block> getBlock( Site site);
	
	Block saveBlock( Block block );

}



