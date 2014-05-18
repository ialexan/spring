package csns.model.site.dao;

import java.util.List;

import csns.model.site.Announcement;
import csns.model.site.Site;

public interface AnnouncementDao {
	
	Announcement getAnnouncementById( Long id );
	
	List<Announcement> getAnnouncement(Site site);
	
	Announcement saveAnnouncement( Announcement announcement );

}
