/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.domain;

import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Map;

public class BuildState {

	private String fullUrl;

	private int number;

	private long queueId;

	private String phase;

	private String status;

	private String url;

	private String displayName;

	private ScmState scm;

	private Map<String, String> parameters;

	private StringBuilder log;

	private final Map<String, Map<String, String>> artifacts = new HashMap<String, Map<String, String>>();

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getQueueId() {
		return queueId;
	}

	public void setQueueId(long queue) {
		this.queueId = queue;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> params) {
		this.parameters = new HashMap<String, String>(params);
	}

	public Map<String, Map<String, String>> getArtifacts() {
		return artifacts;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public ScmState getScm() {
		return scm;
	}

	public void setScm(ScmState scmState) {
		this.scm = scmState;
	}

	public StringBuilder getLog() {
		return this.log;
	}

	public void setLog(StringBuilder log) {
		this.log = log;
	}
}
