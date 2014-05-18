package csns.model.site.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.site.Block;
import csns.model.site.Item;
import csns.model.site.dao.ItemDao;

@Repository
public class ItemDaoImpl implements ItemDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
    public Item getItemById( Long id )
    {
    	return entityManager.find( Item.class, id );
    }
	
	@Override
	public List<Item> getItems(Block block)
	{
		List<Item> items = entityManager
                .createQuery( "from Item item where block = :block order by item.itemOrder", Item.class )
                .setParameter( "block", block )
                .getResultList();
    	 
    	 return items;
	}
	
	
	@Override
    @Transactional
    @PreAuthorize("#item.block.site.section.isInstructor(principal)")
    public Item saveItem( Item item )
    {
    	return entityManager.merge( item );
    }
	

		
	
}
