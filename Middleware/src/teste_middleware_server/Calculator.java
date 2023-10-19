package teste_middleware_server;

import middleware.identification.annotations.Get;

import middleware.identification.annotations.Post;
import middleware.identification.annotations.RequestMap;
import org.json.JSONObject;
import java.io.Serializable;

@RequestMap(router = "/calc")
public class Calculator implements Serializable {

    @Get(router = "/add")
    public JSONObject add(JSONObject jsonObject) throws Throwable {
        float a = jsonObject.getLong("var1");
        float b = jsonObject.getLong("var2");

        JSONObject result = new JSONObject();
        result.put("result", a+b);

        return result;
    }

    @Post(router = "/sub")
    public JSONObject sub(JSONObject jsonObject) throws Throwable {
        float a = jsonObject.getLong("var1");
        float b = jsonObject.getLong("var2");

        JSONObject result = new JSONObject();
        result.put("result", a-b);

        return result;
    }
}
