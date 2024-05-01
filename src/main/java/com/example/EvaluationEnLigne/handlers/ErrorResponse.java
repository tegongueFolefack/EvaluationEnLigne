package com.example.EvaluationEnLigne.handlers;

import java.time.Instant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

	
	@Data
	@NoArgsConstructor @AllArgsConstructor
	@Builder
	public class ErrorResponse {
		 private int status;
		    private String error;
		    private Instant timestamp;
		    private String message;
		    private String path;
			public int getStatus() {
				return status;
			}
			public void setStatus(int status) {
				this.status = status;
			}
			public String getError() {
				return error;
			}
			public void setError(String error) {
				this.error = error;
			}
			public Instant getTimestamp() {
				return timestamp;
			}
			public void setTimestamp(Instant timestamp) {
				this.timestamp = timestamp;
			}
			public String getMessage() {
				return message;
			}
			public void setMessage(String message) {
				this.message = message;
			}
			public String getPath() {
				return path;
			}
			public void setPath(String path) {
				this.path = path;
			}
			

}
