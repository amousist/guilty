package io.github.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class FailedExecution {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private boolean muted;
	private int buildNumber;
	private String jobName;
	private String commit;
	private Date date;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "scmuser_failedexecution", joinColumns = @JoinColumn(name = "failedexecution_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "scmuser_id", referencedColumnName = "id"))
	private Set<ScmUser> scmUsers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(String commit) {
		this.commit = commit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<ScmUser> getScmUsers() {
		return scmUsers;
	}

	public void setScmUsers(Set<ScmUser> scmUsers) {
		this.scmUsers = scmUsers;
	}

	public int getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

}
