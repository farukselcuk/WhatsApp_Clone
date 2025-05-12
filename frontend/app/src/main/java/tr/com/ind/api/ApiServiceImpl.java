package tr.com.ind.api;

import tr.com.ind.api.dto.request.UserLoginRequest;

public class ApiServiceImpl {

    public void login(UserLoginRequest loginRequest){
        UserApiService client = ApiClient.getClient().create(UserApiService.class);
    }
}
