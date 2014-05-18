package csns.model.site.dao;

import java.util.List;

import csns.model.academics.Course;
import csns.model.academics.Quarter;
import csns.model.academics.Section;
import csns.model.site.Site;

public interface SiteDao {

    Site getSiteById( Long id );
    
    List<Site> getAllSites();

    Site getSite( Section section );

    Site getSite( Quarter quarter, Course course, int SectionNumber );
    
    List<Site> getSitesByQuarter( int quarterCode );

    Site saveSite( Site site );

}