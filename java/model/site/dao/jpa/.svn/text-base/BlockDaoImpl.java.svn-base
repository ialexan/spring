package csns.model.site.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.site.Block;
import csns.model.site.Site;
import csns.model.site.dao.BlockDao;

@Repository
public class BlockDaoImpl implements BlockDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
    public Block getBlockById( Long id )
    {
    	return entityManager.find( Block.class, id );
    }
	
	@Override
	public List<Block> getBlock(Site site)
	{
		List<Block> blocks = entityManager
                .createQuery( "from Block where site = :site", Block.class )
                .setParameter( "site", site )
                .getResultList();
    	 
    	 return blocks;
	}

    @Override
    @Transactional
    @PreAuthorize("#block.site.section.isInstructor(principal)")
    public Block saveBlock( Block block )
    {
    	return entityManager.merge( block );
    }

}
