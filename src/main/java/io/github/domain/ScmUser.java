package io.github.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
public class ScmUser implements Comparable<ScmUser>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String username;

	@ManyToMany(mappedBy = "scmUsers")
	private Set<FailedExecution> failedExecutions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<FailedExecution> getFailedExecutions() {
		return failedExecutions;
	}

	public void setFailedExecutions(Set<FailedExecution> failedExecutions) {
		this.failedExecutions = failedExecutions;
	}

	@Override
	public int compareTo(ScmUser other) {
		if (this.failedExecutions4Report() < other.failedExecutions4Report()) return 1;
		if (this.failedExecutions4Report() > other.failedExecutions4Report()) return -1;
		
		return 0;
	}
	
	public int failedExecutions4Report(){
		int feAmount = 0;
		for (FailedExecution fe : failedExecutions){
			if (!fe.isMuted()) feAmount++;
		}
		return feAmount;
	}
}
