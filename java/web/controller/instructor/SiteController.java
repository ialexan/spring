package csns.web.controller.instructor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import csns.model.academics.Course;
import csns.model.academics.Quarter;
import csns.model.academics.Section;
import csns.model.academics.dao.CourseDao;
import csns.model.academics.dao.QuarterDao;
import csns.model.academics.dao.SectionDao;
import csns.model.core.File;
import csns.model.core.User;
import csns.model.core.dao.FileDao;
import csns.model.site.Announcement;
import csns.model.site.Block;
import csns.model.site.Item;
import csns.model.site.ItemSearched;
import csns.model.site.Site;
import csns.model.site.dao.AnnouncementDao;
import csns.model.site.dao.BlockDao;
import csns.model.site.dao.ItemDao;
import csns.model.site.dao.ItemSearchedDao;
import csns.model.site.dao.SiteDao;
import csns.security.SecurityUtils;
import csns.util.ApplicationProperties;

@Controller("instructor/SiteController")
public class SiteController {

	@Autowired
	private SiteDao siteDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private SectionDao sectionDao;

	@Autowired
	private QuarterDao quarterDao;

	@Autowired
	private BlockDao blockDao;

	@Autowired
	private AnnouncementDao announcementDao; 

	@Autowired
	private ItemDao itemDao;

	@Autowired
	ApplicationProperties applicationProperties;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private ItemSearchedDao itemSearchedDao;

	private Logger logger = LoggerFactory.getLogger( SiteController.class );


	@RequestMapping("/{quarterString}/{courseCode}/{sectionNumber}/")
	public String viewSite( @PathVariable("quarterString") String quarterString,
			@PathVariable("courseCode") String courseCode, @PathVariable("sectionNumber") String sectionNumber,
			HttpSession session, ModelMap models, ServletRequest req)
	{
		String term = quarterString.substring(0, quarterString.length()-4);

		int year = Integer.parseInt( quarterString.substring(quarterString.length()-4, quarterString.length()) );

		Quarter quarter = new Quarter(year, term);


		Course course = courseDao.getCourseByCode( courseCode );

		int sectionNumberInt = Integer.parseInt(sectionNumber.substring(7, sectionNumber.length()));


		Site site = siteDao.getSite( quarter, course, sectionNumberInt );


		
		//---This is to check if the instructor is the class instructor
		Long sectionNumberLong = Long.parseLong(sectionNumber.substring(7, sectionNumber.length()));
		
		User currentUserLoggedIn;
		try{
			currentUserLoggedIn = SecurityUtils.getCurrentUser();
		}
		catch( ClassCastException e )
		{
			currentUserLoggedIn = null;
		}
		
		String userIsInstructor = "n";
		
		if (site == null){
			models.addAttribute( "site", "nothing");
			models.addAttribute( "quarterTerm", term );
			models.addAttribute( "quarterYear", year );
			models.addAttribute( "courseCode", courseCode );
			models.addAttribute( "sectionNumber", sectionNumberInt );
			
			if (currentUserLoggedIn != null){
				Object[] params = {quarter,course,sectionNumberLong};
				List<Section> sectionList = sectionDao.getSectionsByInstructor(currentUserLoggedIn, params);

			
				if (sectionList != null){
					userIsInstructor = "y";
				}
			}	
			else {
				userIsInstructor = "n";
			}
			
		    //------------
		}
		else{
			// Need to display an ordered item
			for(Block block: site.getBlocks()){
				List<Item> itemList = itemDao.getItems(block); 
				block.setItems(itemList);
			}
		
			if (currentUserLoggedIn != null){
				if (site.getSection().isInstructor(currentUserLoggedIn)){
					userIsInstructor = "y";
				}
			}	
			else {
				userIsInstructor = "n";
			}
		    //------------

			models.addAttribute( "site", site );
		}

		
		
		
		
     

		models.addAttribute( "userIsInstructor", userIsInstructor );
		return "instructor/viewSite"; 

	}

	// Add a Class Website	

	@RequestMapping(value = "/instructor/addSite.html", method = RequestMethod.GET)
	public String addSite( @RequestParam String quarterTerm, @RequestParam Integer quarterYear, 
			@RequestParam String courseCode, @RequestParam Integer sectionNumber, ModelMap models )
	{	

		Quarter quarter = new Quarter(quarterYear, quarterTerm);


		Course course = courseDao.getCourseByCode( courseCode );

		User instructor = SecurityUtils.getCurrentUser();


		Object[] params = {quarter, course, sectionNumber};
		List<Section> sections = sectionDao.getSectionsByInstructor(instructor, params);

		Site site = new Site();

		site.setSection(sections.get(0));


		models.addAttribute( "site", site );
		return "instructor/addSite";
	}

	@RequestMapping(value = "/instructor/addSite.html", method = RequestMethod.POST)
	public String addSite( @ModelAttribute Site site, @RequestParam Long sectionId,
			SessionStatus sessionStatus)
	{

		Section section = sectionDao.getSectionById( sectionId );
		site.setSection(section);

		siteDao.saveSite(site);

		sessionStatus.setComplete();
		return "redirect:/sites/" + site.getSection().getQuarter().getQuarterName() + site.getSection().getQuarter().getYear() + "/" + site.getSection().getCourse().getCode() + "/section" + site.getSection().getNumber() + "/"; // Change to the sites/quarter/course/section1


	}


	// Add a Blocks to the class website    


	@RequestMapping(value = "/instructor/addBlock.html", method = RequestMethod.GET)
	public String addBlock( @RequestParam Long siteId, ModelMap models )
	{	
		// From the site id get the site 
		Site site = siteDao.getSiteById(siteId);

		Block block = new Block();
		block.setSite(site);
		site.getBlocks().add(block);

		models.addAttribute( "block", block );
		return "instructor/addBlock";
	}

	@RequestMapping(value = "/instructor/addBlock.html", method = RequestMethod.POST)
	public String addBlock( @ModelAttribute Block block, @RequestParam Long siteId,
			SessionStatus sessionStatus )
	{
		Site site = siteDao.getSiteById(siteId);

		block.setSite(site);
		blockDao.saveBlock(block);

		return "redirect:/sites/" + site.getSection().getQuarter().getQuarterName() + site.getSection().getQuarter().getYear() + "/" + site.getSection().getCourse().getCode() + "/section" + site.getSection().getNumber() + "/"; // Change to the sites/quarter/course/section1		
	}

	// Add Announcements to the Class Website    

	@RequestMapping(value = "/instructor/addAnnouncement.html", method = RequestMethod.GET)
	public String addAnnouncement( @RequestParam Long siteId, ModelMap models )
	{	
		// From the site id get the site 
		Site site = siteDao.getSiteById(siteId);

		Announcement announcement = new Announcement();
		announcement.setSite(site);
		site.getAnnouncements().add(announcement);

		models.addAttribute( "announcement", announcement);
		return "instructor/addAnnouncement";
	}

	@RequestMapping(value = "/instructor/addAnnouncement.html", method = RequestMethod.POST)
	public String addAnnouncement( @ModelAttribute Announcement announcement, @RequestParam Long siteId,
			SessionStatus sessionStatus )
	{
		Site site = siteDao.getSiteById(siteId);

		announcement.setSite(site);

		Date now = new Date();
		announcement.setTimestamp(now);

		//-----------------------


		//--------- Setup an email to send the announcement to the students


		// Find the instructor's email address
		User instructor = SecurityUtils.getCurrentUser();
		String from = instructor.getEmail1();

		// Find the course code of the class and include it in the subject along with the date of the announcement 
		String subject = site.getSection().getCourse().getCode() + " Announcement " + new SimpleDateFormat("MM/dd/yyyy").format(announcement.getTimestamp());


		// Need to find all the students in the class
		//For testing purposes I am using cysun
		//String to = "cysun@localhost.localdomain";
		Iterator<User> students = site.getSection().getStudents().iterator();
		InternetAddress[] addressTo = new InternetAddress[site.getSection().getStudents().size()];
		int i = 0;
		while (students.hasNext()) {
			try {
				addressTo[i] = new InternetAddress(students.next().getEmail1());
			} catch (AddressException e) {
				e.printStackTrace();
			}
			++i;
		}


		// The content of the email
		String content = instructor.getName() + " posted the following announcement on the " + site.getSection().getCourse().getCode() + " class website at " +
				"http://localhost:8080/csns2/sites/" + site.getSection().getQuarter().getQuarterName() + site.getSection().getQuarter().getYear() + "/" +
				site.getSection().getCourse().getCode() + "/section" + site.getSection().getNumber() + "/ <br /><br />&nbsp;&nbsp;&nbsp;&nbsp;" + 
				announcement.getAnnouncementContent();



		// ---- Now Email
		Properties props = System.getProperties();
		props.put( "mail.smtp.host", "localhost" );
		Session session = Session.getInstance( props );

		Message msg = new MimeMessage( session );
		try
		{
			msg.setFrom( new InternetAddress( from ) );
			msg.setRecipients( RecipientType.TO, addressTo );
			msg.setSubject( subject );
			msg.setContent(content, "text/html");
			//msg.setText( content);

			Transport.send( msg );
		}
		catch( Exception e )
		{
			new Exception( e );
		}


		//-----------------------

		announcementDao.saveAnnouncement(announcement);
		return "redirect:/sites/" + site.getSection().getQuarter().getQuarterName() + site.getSection().getQuarter().getYear() + "/" + site.getSection().getCourse().getCode() + "/section" + site.getSection().getNumber() + "/"; // Change to the sites/quarter/course/section1		

	}



	/////////////////////////////////////////////	
	// Add Items

	@RequestMapping(value = "/instructor/addItem.html", method = RequestMethod.GET)
	public String addItem( @RequestParam(value = "itemType", required = false) Integer itemType, HttpSession session, 
			@RequestParam Long blockId, ModelMap models )
	{	

		if( itemType == null || itemType == 1){
			itemType = 1;
		}
		else if(itemType == 2){
			itemType = 2;			
		}
		else if (itemType == 3){
			itemType = 3;
		}


		// From the block id get the block 
		Block block = blockDao.getBlockById(blockId);

		Item item = new Item();
		item.setBlock(block);
		block.getItems().add(item);

		models.addAttribute( "item", item);
		models.addAttribute( "itemType", itemType);
		return "instructor/addItem";
	}

	@RequestMapping(value = "/instructor/addItem.html", method = RequestMethod.POST)
	public String addItem( @ModelAttribute Item item, @RequestParam Long blockId, @RequestParam Integer itemType,
			@RequestParam(required = false) MultipartFile uploadedFile, SessionStatus sessionStatus )
	{
		Block block = blockDao.getBlockById(blockId);
		item.setBlock(block);

		item.setItemType(itemType);

		// Saving the File
		if (itemType == 3)
		{	

			if( uploadedFile == null || uploadedFile.isEmpty() ){
				return "redirect:/instructor/addItem.html?blockId="+blockId; 	
			}

			User user = SecurityUtils.getCurrentUser();
			String fileName = uploadedFile.getOriginalFilename();

			File file = new File();
			file.setName( fileName );

			file.setOwner( user );

			file.setType( uploadedFile.getContentType() );
			file.setSize( uploadedFile.getSize() );
			file.setDate( new Date() );
			file = fileDao.saveFile( file );

			try
			{
				file.setBaseDir( applicationProperties.getProperty( "app.dir.file" ) );
				uploadedFile.transferTo( file.getDiskFile() );
			}
			catch( IOException e )
			{
				logger.warn( "File transfer failed", e );
			}

			item.setFileContent(file);

		}


		// This is check for the order of the item in the block

		List<Item> allItems = itemDao.getItems(block);

		if (allItems == null){
			item.setItemOrder(1);
		}
		else{
			Integer max=0;
			for (Item itemSpecific: allItems){
				if (itemSpecific.getItemOrder() > max){
					max = itemSpecific.getItemOrder();
				}
			}
			item.setItemOrder(max+1);
		}


		itemDao.saveItem(item);

		return "redirect:/sites/" + item.getBlock().getSite().getSection().getQuarter().getQuarterName() + item.getBlock().getSite().getSection().getQuarter().getYear() + "/" + 
		item.getBlock().getSite().getSection().getCourse().getCode() + "/section" + item.getBlock().getSite().getSection().getNumber() + "/"; 		

	}


	// Display a Text Item
	@RequestMapping("/diplayTextItem.html")
	public String diplayTextItem( @RequestParam Long itemId , ModelMap models )
	{

		Item item = itemDao.getItemById(itemId);

		models.addAttribute( "item", item );

		return "instructor/diplayTextItem"; 

	}


	//----------Editing an Item


	@RequestMapping(value = "/instructor/editItem.html", method = RequestMethod.GET)
	public String editItem( @RequestParam(value = "itemType", required = false) Integer itemType, HttpSession session, 
			@RequestParam Long itemId, ModelMap models )
	{	
		// From the site id get the site 
		Item item = itemDao.getItemById(itemId);

		if (itemType == null){
			itemType = item.getItemType();
		}
		else if(itemType == 1){
			itemType = 1;
		}
		else if(itemType == 2){
			itemType = 2;			
		}
		else if (itemType == 3){
			itemType = 3;
		}

		models.addAttribute( "item", item);
		models.addAttribute( "itemType", itemType);
		return "instructor/editItem";
	}	


	@RequestMapping(value = "/instructor/editItem.html", method = RequestMethod.POST)
	public String editItem( @ModelAttribute Item item, @RequestParam Integer itemType, @RequestParam Long blockId, @RequestParam Long itemId,
			@RequestParam(required = false) MultipartFile uploadedFile, SessionStatus sessionStatus )
	{
		// From the site id get the site 
		Item itemEdited = itemDao.getItemById(itemId);

		if (itemEdited.getItemType() == 3){
			itemEdited.setFileContent(null);
		}

		//Block block = blockDao.getBlockById(blockId);
		//item.setBlock(block);

		itemEdited.setItemType(itemType);
		itemEdited.setName(item.getName());

		if (itemType == 1){
			itemEdited.setStringContent(item.getStringContent());
			itemEdited.setUrlContent(null);
			itemEdited.setFileContent(null);
		}
		else if (itemType == 2){
			itemEdited.setStringContent(null);
			itemEdited.setUrlContent(item.getUrlContent());
			itemEdited.setFileContent(null);
		}
		// Saving the File
		if (itemType == 3)
		{	
			itemEdited.setStringContent(null);
			itemEdited.setUrlContent(null);

			if( uploadedFile == null || uploadedFile.isEmpty() ){
				return "redirect:/instructor/editItem.html?itemId="+itemEdited.getId(); 	
			}

			User user = SecurityUtils.getCurrentUser();
			String fileName = uploadedFile.getOriginalFilename();

			File file = new File();
			file.setName( fileName );

			file.setOwner( user );

			file.setType( uploadedFile.getContentType() );
			file.setSize( uploadedFile.getSize() );
			file.setDate( new Date() );
			file = fileDao.saveFile( file ); 

			try
			{
				file.setBaseDir( applicationProperties.getProperty( "app.dir.file" ) );
				uploadedFile.transferTo( file.getDiskFile() );
			}
			catch( IOException e )
			{
				logger.warn( "File transfer failed", e );
			}

			itemEdited.setFileContent(file);

		}

		itemDao.saveItem(itemEdited);

		return "redirect:/sites/" + itemEdited.getBlock().getSite().getSection().getQuarter().getQuarterName() + itemEdited.getBlock().getSite().getSection().getQuarter().getYear() + "/" + 
		itemEdited.getBlock().getSite().getSection().getCourse().getCode() + "/section" + itemEdited.getBlock().getSite().getSection().getNumber() + "/"; 		


	}


	// Listing the class websites

	@RequestMapping(value = "/classWebsites.html")
	public String classWebsites( HttpSession session, ModelMap models)
	{	
		// Only quarters for which there are class websites should be shown in the listing. 
		List<Quarter> allQuarters = quarterDao.getAllQuarters();

		List<Site> allSites = siteDao.getAllSites();

		Boolean contains = false;

		List<Quarter> quarters = new ArrayList<Quarter>();

		for (Quarter quarter: allQuarters){
			for (Site site: allSites){
				if (site.getSection().getQuarter().getCode()==quarter.getCode()){
					contains = true;
				}
			}
			if (contains){
				quarters.add(quarter);
			}
			contains = false;
		}

		models.addAttribute( "quarters", quarters);

		return "instructor/classWebsites";


	}


	@RequestMapping(value = "/getQuarter.html")
	public String getQuarter( @RequestParam(value = "quarterCode", required = false) Integer quarterCode, 
			HttpSession session, ModelMap models)
	{
		List<Quarter> quarters = quarterDao.getAllQuarters();

		if( quarterCode == null )
		{
			quarterCode = quarters.get(0).getCode();
		}    

		List<Site> sites = siteDao.getSitesByQuarter(quarterCode);

		models.addAttribute( "sites", sites);

		return "instructor/getQuarter";

	}


	// Searching for an Item

	@RequestMapping(value = "/searchItems.html")
	public String searchItems( @RequestParam String query, @RequestParam Long siteId,   
			HttpSession session, ModelMap models)
	{	

		List<ItemSearched> items = itemSearchedDao.searchItem(query, siteId); 

		models.addAttribute( "items", items);
		return "instructor/searchItems";

	}



	// Ordering Items

	@RequestMapping(value = "/orderingItems.html")
	public String orderingItems( @RequestParam(value = "order", required = false) String order,
			HttpSession session, ModelMap models)
	{	
		String[] orderArr;
		String delimiter = ",";

		orderArr = order.split(delimiter);

		for(int i=0; i < orderArr.length ; i++){
			Long itemId = Long.parseLong(orderArr[i]);		
			Item item = itemDao.getItemById( itemId );
			item.setItemOrder(i+1);
			itemDao.saveItem(item);
		}



		//models.addAttribute( "order", orderArr[0]);
		return "instructor/orderItems";

	}



}
