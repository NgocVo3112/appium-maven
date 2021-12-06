package test_data;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataObjectBuilder {
    public static LoginCreds[] buildCredObject(String jsonDataFileLocation){
        LoginCreds[] loginCreds = null;
        String currentProjectLocation = System.getProperty("user.dir");
        try(
                Reader jsonContentReader = Files.newBufferedReader(Paths.get(currentProjectLocation + jsonDataFileLocation))
        ) {
            Gson gson = new Gson();
            loginCreds = gson.fromJson(jsonContentReader, LoginCreds[].class);

        }catch (Exception e){
            e.printStackTrace();
        }
        return loginCreds;
    }

    public static void main(String[] args) {
        String jsonDataFileLocation = "/src/main/resources/test-data/loginCreds.json";
        LoginCreds[] credsData = DataObjectBuilder.buildCredObject(jsonDataFileLocation);
        for (LoginCreds credsDatum : credsData) {
            System.out.println(credsDatum);
        }
    }
}
