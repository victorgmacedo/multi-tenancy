package br.com.multitenancy.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConnectionValues {

	private String username;
	private String password;
	private String jdbcUrl;
	
}
