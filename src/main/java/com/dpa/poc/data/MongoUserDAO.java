package com.dpa.poc.data;

import com.dpa.poc.entity.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoUserDAO {
    MongoCollection<User> collection;

    public MongoUserDAO(String url){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register("com.dpa.poc.User").automatic(true).build()));

        //"mongodb://localhost:27017"
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        collection = database.getCollection("user", User.class).withCodecRegistry(pojoCodecRegistry);
    }

    public boolean create(User user){
        collection.insertOne(user);

        return true;
    }

    public User getUserByName(String name){
        User obtainedUser = collection.find(eq("name", name)).first();
        System.out.println("Obtained User is not Null: "+String.valueOf(obtainedUser != null));

        return obtainedUser;
    }

}
