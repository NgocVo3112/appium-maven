package test_data;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataObjectBuilder {
    public static <T> T buildDataObject(String jsonDataFileLocation, Class<T> dataType ){
        String currentProjectLocation = System.getProperty("user.dir");
        try(
                Reader jsonContentReader = Files.newBufferedReader(Paths.get(currentProjectLocation + jsonDataFileLocation))
        ) {
            Gson gson = new Gson();
            return gson.fromJson(jsonContentReader, dataType);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
