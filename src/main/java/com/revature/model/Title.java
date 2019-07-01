package com.revature.model;

public class Title implements Comparable<Title> {
	
	private long titleId;
	private String titleName;
	
	public Title() {}

	public Title(long titleId, String titleName) {
		this.titleId = titleId;
		this.titleName = titleName;
	}

	public long getTitleId() {
		return titleId;
	}

	public void setTitleId(long titleId) {
		this.titleId = titleId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (titleId ^ (titleId >>> 32));
		result = prime * result + ((titleName == null) ? 0 : titleName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Title other = (Title) obj;
		if (titleId != other.titleId)
			return false;
		if (titleName == null) {
			if (other.titleName != null)
				return false;
		} else if (!titleName.equals(other.titleName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Title [titleId=" + titleId + ", titleName=" + titleName + "]";
	}

	@Override
	public int compareTo(Title o) {
		//return this.name.compareTo(o.name); <= Alternative method
		return new Long(this.titleId).compareTo(o.titleId);
	}

}
