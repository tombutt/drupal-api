package com.redhat.drupal;

import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.InputSource;

import com.redhat.drupal.exceptions.InvalidNodeXmlException;
import com.redhat.drupal.fields.Field;


public abstract class Node {
	protected Integer nid;  // Node ID
	protected Integer uid;  // The users.uid that owns this node; initially, this is the user that created it.
	protected String title;  // Title of the node
	protected String log;  // last revision log message
	protected Boolean status;  // Boolean flag if this has a published revision or not
	protected Integer comment;  // how many comments does this node have
	protected Integer vid;  // current revision ID of this node
	protected String type;  // content type
	protected String language;  // language of this node and revision
	protected Integer created;  // timestamp of when this node was created
	protected Integer changed;  // timestamp of when this node was last updated
	protected Integer tnid;  // parent node id of which this node is a translation
	protected Boolean translate;   // A boolean indicating whether this translation page needs to be updated
	protected Integer revisionTimestamp;  // timestimp of the revision
	protected Integer revisionUid;  // uid of revision author
	protected String accessState;  // access state for private, retired, active
	protected String path;  // full URL path to this node
	protected String name;  // username of the person who created this node
	
	private XPathFactory xpathFactory = XPathFactory.newInstance();
	private XPath xpath = xpathFactory.newXPath();

	/**
	 * Parse the out the common node fields from the XML from a services GET request
	 * @param xml
	 */
	public void fromXml(String xml) {
		if (!validNodeXml(xml)) {
			throw new InvalidNodeXmlException("Invalid node XML: " + xml);
		}
		
		this.nid = Utils.safeNewInteger(parseField("/result/nid", xml));
		this.uid = Utils.safeNewInteger(parseField("/result/uid", xml));
		this.title = parseField("/result/title", xml);
		this.log = parseField("/result/log", xml);
		this.status = safeNewBoolean(parseField("/result/status", xml));
		this.comment = Utils.safeNewInteger(parseField("/result/comment", xml));		
		this.vid = Utils.safeNewInteger(parseField("/result/vid", xml));
		this.type = parseField("/result/type", xml);
		this.language = parseField("/result/language", xml);
		this.created = Utils.safeNewInteger(parseField("/result/created", xml));	
		this.changed = Utils.safeNewInteger(parseField("/result/changed", xml));
		this.tnid = Utils.safeNewInteger(parseField("/result/tnid", xml));	
		this.translate = safeNewBoolean(parseField("/result/translate", xml));
		
		//TODO: move these red hat specific fields to the red hat content types library
		this.revisionTimestamp = Utils.safeNewInteger(parseField("/result/revision_timestamp", xml));	
		this.revisionUid = Utils.safeNewInteger(parseField("/result/revision_uid", xml));
		this.accessState = parseField("/result/access_state", xml);
		this.path = parseField("/result/path", xml);
		this.name = parseField("/result/name", xml);
	}
	
	private boolean validNodeXml(String xml) {
		// At the minimum it should have nid, type, title
		//TODO: Better xml validation. In the absense of an xsd this validation is pretty weak
		nid = Utils.safeNewInteger(parseField("/result/nid", xml));
		title = parseField("/result/title", xml);
		type = parseField("/result/type", xml);
		
		if (nid == null || title == null || type == null) {
			return false;
		}
		
		return true;
	}

	private Boolean safeNewBoolean(String booleanString) {
		if (!StringUtils.isBlank(booleanString)	&& (booleanString.equals("0") || booleanString.equals("1"))) {
			if (booleanString.equals("1")) {
				return new Boolean(true);
			} else {
				return new Boolean(false);
			}
		}
		return null;
	}

	private String parseField(String fieldName, String xml) {
		String parsedField = null;
		try {
			parsedField = xpath.evaluate(fieldName, new InputSource(new StringReader(xml)));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} 
		
		if (StringUtils.isBlank(parsedField)) {
			parsedField = null;  
		}
		return parsedField;
	}
	
	protected void appendValidXml(StringBuffer sb, Field field) {
		if (field.isSet()) {
			sb.append(field.toPostXml());
		}
	}

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getComment() {
		return comment;
	}

	public void setComment(Integer comment) {
		this.comment = comment;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getType() {
		return type;
	}

	protected void setType(String type) {
		this.type = type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getCreated() {
		return created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}

	public Integer getChanged() {
		return changed;
	}

	public void setChanged(Integer changed) {
		this.changed = changed;
	}

	public Integer getTnid() {
		return tnid;
	}

	public void setTnid(Integer tnid) {
		this.tnid = tnid;
	}

	public Boolean getTranslate() {
		return translate;
	}

	public void setTranslate(Boolean translate) {
		this.translate = translate;
	}

	public Integer getRevisionTimestamp() {
		return revisionTimestamp;
	}

	public void setRevisionTimestamp(Integer revisionTimestamp) {
		this.revisionTimestamp = revisionTimestamp;
	}

	public Integer getRevisionUid() {
		return revisionUid;
	}

	public void setRevisionUid(Integer revisionUid) {
		this.revisionUid = revisionUid;
	}

	public String getAccessState() {
		return accessState;
	}

	public void setAccessState(String accessState) {
		this.accessState = accessState;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
