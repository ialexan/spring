//Ishag Alexanian
//For the items Searched 

package csns.model.site;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import csns.model.core.File;


@Entity
@Table(name = "items")
public class ItemSearched implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "string_content")
	private String stringContent;

	@Column(name = "url_content")
	private String urlContent;

	@OneToOne
	@JoinColumn(name = "file_content_id")
	private File fileContent;

	@ManyToOne
	@JoinColumn(name = "block_id", nullable = false)
	private Block block;

	@Column(nullable = false, name="item_type")
	private Integer itemType;

	@Column(name = "name_search_result")
	private String nameSearchResult;

	@Column(name = "string_content_search_result")
	private String stringContentSearchResult;

	@Column(name = "url_content_search_result")
	private String urlContentSearchResult;


	public ItemSearched(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public String getStringContent() {
		return stringContent;
	}

	public void setStringContent(String stringContent) {
		this.stringContent = stringContent;
	}

	public String getUrlContent() {
		return urlContent;
	}

	public void setUrlContent(String urlContent) {
		this.urlContent = urlContent;
	}

	public File getFileContent() {
		return fileContent;
	}

	public void setFileContent(File fileContent) {
		this.fileContent = fileContent;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getNameSearchResult() {
		return nameSearchResult;
	}

	public void setNameSearchResult(String nameSearchResult) {
		this.nameSearchResult = nameSearchResult;
	}

	public String getStringContentSearchResult() {
		return stringContentSearchResult;
	}

	public void setStringContentSearchResult(String stringContentSearchResult) {
		this.stringContentSearchResult = stringContentSearchResult;
	}

	public String getUrlContentSearchResult() {
		return urlContentSearchResult;
	}

	public void setUrlContentSearchResult(String urlContentSearchResult) {
		this.urlContentSearchResult = urlContentSearchResult;
	}




}

