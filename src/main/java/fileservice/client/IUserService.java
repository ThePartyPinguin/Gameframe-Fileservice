package fileservice.client;

import fileservice.model.dto.FullUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("gameframe-user-service")
public interface IUserService {

    @RequestMapping(value = "/internal/getuser/{userId}", method = RequestMethod.GET)
    FullUserResponse getUserById(@PathVariable long userId);

}
