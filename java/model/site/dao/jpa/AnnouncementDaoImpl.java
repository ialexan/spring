package csns.model.site.dao.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import csns.model.site.Announcement;
import csns.model.site.Site;
import csns.model.site.dao.AnnouncementDao;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	
	@Override
    public Announcement getAnnouncementById( Long id ){
		
		return entityManager.find( Announcement.class, id );
		
	}
	
	@Override
	public List<Announcement> getAnnouncement(Site site)
	{
		List<Announcement> announcements = entityManager
                .createQuery( "from Annoucement where site = :site", Announcement.class )
                .setParameter( "site", site )
                .getResultList();
    	 
    	 return announcements;
	}
	
	@Override
    @Transactional
    @PreAuthorize("#announcement.site.section.isInstructor(principal)")
    public Announcement saveAnnouncement( Announcement announcement ){
		
		return entityManager.merge(announcement);
		
	}

}
