package csns.model.site.dao;

import java.util.List;

import csns.model.site.ItemSearched;

public interface ItemSearchedDao {
	
	List<ItemSearched> searchItem(String query, Long siteId);

}
