package csns.model.site.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import csns.model.site.ItemSearched;
import csns.model.site.dao.ItemSearchedDao;

@Repository
public class ItemSearchedDaoImpl implements ItemSearchedDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ItemSearched> searchItem(String query, Long siteId){
		List<ItemSearched> Allitems =  entityManager
				.createNamedQuery("site.item.search", ItemSearched.class)
				.setParameter( "query", query )
				.getResultList();
		
		List<ItemSearched> items = new ArrayList<ItemSearched>();
		
		for (ItemSearched item: Allitems){
			if(item.getBlock().getSite().getId().equals(siteId)){
				items.add(item);
			}
		}
		
		return items;
	}

}
