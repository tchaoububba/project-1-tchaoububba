package com.revature.model;

public class Request implements Comparable<Request> {
	
	private long requestId;
	private Employee employee;
	private String requestBody;
	private Status status;
	
	public Request() {
		this.requestBody = "";
	}
	
	public Request(long requestId) {
		this();
		this.requestId = requestId;
	}

	public Request(long requestId, Employee employee) {
		this();
		this.requestId = requestId;
		this.employee = employee;
	}
	
	public Request(long requestId, Employee employee, String requestBody, Status status) {
		this.requestId = requestId;
		this.employee = employee;
		this.requestBody = requestBody;
		this.status = status;
	}

	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((requestBody == null) ? 0 : requestBody.hashCode());
		result = prime * result + (int) (requestId ^ (requestId >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Request other = (Request) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (requestBody == null) {
			if (other.requestBody != null)
				return false;
		} else if (!requestBody.equals(other.requestBody))
			return false;
		if (requestId != other.requestId)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", employee=" + employee + ", requestBody=" + requestBody
				+ ", status=" + status + "]";
	}

	@Override
	public int compareTo(Request o) {
		return new Long(this.requestId).compareTo(o.requestId);
	}	
}
