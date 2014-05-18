package csns.model.site.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import csns.model.academics.Course;
import csns.model.academics.Quarter;
import csns.model.academics.Section;
import csns.model.site.Site;
import csns.model.site.dao.SiteDao;

@Repository
@SuppressWarnings("unchecked")
public class SiteDaoImpl implements SiteDao {
	
	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public Site getSiteById( Long id )
    {
    	return entityManager.find( Site.class, id );
    }
    
    @Override
    public List<Site> getAllSites(){
    	List<Site> sites = entityManager
                .createQuery( "from Site", Site.class )
                .getResultList();
    	 
    	 return sites;
    }

    @Override
    public Site getSite( Section section )
    {
    	 List<Site> sites = entityManager
                .createQuery( "from Site where section = :section", Site.class )
                .setParameter( "section", section )
                .getResultList();
    	 
    	 return sites.size() == 0 ? null : sites.get(0);
    	 
    }
    
    @Override
    public List<Site> getSitesByQuarter( int quarterCode )
    {
    	String query = "select site from Site site "
                + "join site.section section where section.quarter.code = :quarterCode "
                + "order by section.course, section.number asc";
    	
    	List<Site> sites = entityManager
        		.createQuery( query )
        		.setParameter( "quarterCode", quarterCode )
        		.getResultList();
    	
    	return sites;
    }

	@Override
    public Site getSite( Quarter quarter, Course course, int SectionNumber )
    {
    	
    	String query = "select site from Site site "
                + "join site.section section "
                + "where section.quarter = :quarter and section.course = :course and section.number = :SectionNumber";

        List<Site> sites = entityManager
        		.createQuery( query )
        		.setParameter( "quarter", quarter )
        		.setParameter( "course", course )
        		.setParameter( "SectionNumber", SectionNumber )
        		.getResultList();
        
        return sites.size() == 0 ? null : sites.get(0);
        
    }

    @Override
    @Transactional
    @PreAuthorize("#site.section.isInstructor(principal)")
    public Site saveSite( Site site )
    {
    	return entityManager.merge( site );
    }

}