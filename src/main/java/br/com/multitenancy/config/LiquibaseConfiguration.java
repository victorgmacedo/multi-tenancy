package br.com.multitenancy.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

@Configuration
public class LiquibaseConfiguration {

	private Liquibase liquibase;

	@PostConstruct
	public void postConstruct() throws SQLException, LiquibaseException {
		List<Connection> connections = openConnections(connectionValuesDev(), connectionValuesProd());
		for(Connection conn : connections) {
			Database database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(conn));
			liquibase = new Liquibase("classpath:db/master.xml", new ClassLoaderResourceAccessor(), database);
			liquibase.update(new Contexts(), new LabelExpression());
		}
	}

	private List<Connection> openConnections(ConnectionValues... connections) {
		try {
			List<Connection> conns = new ArrayList<>(connections.length);
			for (ConnectionValues conn : connections) {
				conns.add(DriverManager.getConnection(conn.getJdbcUrl(), conn.getUsername(),
						conn.getPassword()));
			}
			return conns;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Bean(name = "connectionValuesDev")
	@ConfigurationProperties("app.datasource.dev")
	protected ConnectionValues connectionValuesDev() {
		return new ConnectionValues();
	}

	@Bean(name = "connectionValuesProd")
	@ConfigurationProperties("app.datasource.prod")
	protected ConnectionValues connectionValuesProd() {
		return new ConnectionValues();
	}
}
