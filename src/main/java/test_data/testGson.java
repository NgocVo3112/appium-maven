package test_data;

import com.google.gson.Gson;

public class testGson {
    public static void main(String[] args) {
        String jsonObject = "{\n" +
                "  \"username\" : \"teo@sth.com\",\n" +
                "  \"password\" : \"12345678\"\n" +
                "}";
        Gson gson = new Gson();
        LoginCreds loginCreds = gson.fromJson(jsonObject, LoginCreds.class );
        System.out.println(loginCreds);
        System.out.println(gson.toJson(loginCreds));
    }
}
