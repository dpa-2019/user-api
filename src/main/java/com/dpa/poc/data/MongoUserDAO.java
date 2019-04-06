package com.dpa.poc.data;

import com.dpa.poc.entity.RegisterUser;
import com.dpa.poc.entity.User;
import com.dpa.poc.exception.UserExistsException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Updates.*;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class MongoUserDAO {
    MongoCollection<RegisterUser> collection;

    public MongoUserDAO(String url){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().register("com.dpa.poc.RegisterUser").automatic(true).build()));

        //"mongodb://localhost:27017"
        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        collection = database.getCollection("user", RegisterUser.class).withCodecRegistry(pojoCodecRegistry);
    }

    public boolean create(RegisterUser user) throws UserExistsException {

        User existingUser = getUserByEmail(user.getEmail());

        if(existingUser == null){
            collection.insertOne(user);
            return true;
        } else{
            System.out.println("User already exists for "+user.getEmail());
            throw new UserExistsException(user.getEmail());
        }

    }

    public RegisterUser getUserByEmail(String email){
        RegisterUser obtainedUser = collection.find(eq("email", email)).first();
        System.out.println("Obtained User is not Null: "+String.valueOf(obtainedUser != null));
        //System.out.println("User: "+String.valueOf(obtainedUser.getFirstname()+" "+obtainedUser.getEmail()));

        return obtainedUser;
    }

}
