package com.julio.poc.microservices.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {

	@NotEmpty(message = "Application Name is required")
	private String appName;

}