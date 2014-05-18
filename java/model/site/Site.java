//Ishag Alexanian Site

package csns.model.site;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import csns.model.academics.Section;

@Entity
@Table(name = "sites")
public class Site implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "section_id" , nullable = false)
	private Section section;

	@Column(name = "lecture_room")
	private String lectureRoom;

	@Column(name = "lecture_hours")
	private String lectureHours;

	@Column(name = "office_hours")
	private String officeHours;

	@OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
	@OrderBy("id asc")
	private List<Block> blocks;
	
	@OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
	@OrderBy("id asc")
	private List<Announcement> announcements;
	
	public Site(){
		blocks = new ArrayList<Block>();
		announcements = new ArrayList<Announcement>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLectureRoom() {
		return lectureRoom;
	}

	public void setLectureRoom(String lectureRoom) {
		this.lectureRoom = lectureRoom;
	}

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}

	public String getLectureHours() {
		return lectureHours;
	}

	public void setLectureHours(String lectureHours) {
		this.lectureHours = lectureHours;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}	
	

}
