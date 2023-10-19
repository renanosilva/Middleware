package teste_middleware_client;

import org.json.JSONObject;

public interface Calculator{   
    public JSONObject add(JSONObject jsonObject)  throws Throwable;
    public JSONObject sub(JSONObject jsonObject) throws Throwable;
}
