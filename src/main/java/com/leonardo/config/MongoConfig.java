package com.leonardo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.ui.Model;

import com.leonardo.properties.MongoProperties;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.leonardo.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {
	
	@Autowired
	private MongoProperties mongoProps;
	
	@Bean
	public SimpleMongoClientDatabaseFactory mongoDbFactory() {
		SimpleMongoClientDatabaseFactory simpleMongoDbFactory = new SimpleMongoClientDatabaseFactory(mongoClient(), mongoProps.getDatabase());
		return simpleMongoDbFactory;
	}
	
    @Override
    public com.mongodb.client.MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoProps.getUrl());
        MongoCredential credential = MongoCredential.createCredential(mongoProps.getUsername(), getDatabaseName(), mongoProps.getPassword().toCharArray());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .credential(credential)
            .build();
        
        return MongoClients.create(mongoClientSettings);
    }

	@Bean
	public MongoTemplate mongoTemplate() throws Exception{

		return new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory()),
				new MongoMappingContext());
		converter.setTypeMapper(typeMapper);
		List<Converter<Model, BasicDBObject>> convert = new ArrayList<>();
		converter.setCustomConversions(new CustomConversions(convert));
		return converter;
	}

	@Override
	protected String getDatabaseName() {
		return mongoProps.getDatabase();
	}
	
	
}
