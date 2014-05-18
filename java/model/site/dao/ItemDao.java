package csns.model.site.dao;

import java.util.List;

import csns.model.site.Block;
import csns.model.site.Item;

public interface ItemDao {
	
	Item getItemById( Long id );
	
	List<Item> getItems( Block block);
	
	Item saveItem( Item item );
	

}
