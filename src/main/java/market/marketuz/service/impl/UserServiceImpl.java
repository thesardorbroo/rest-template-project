package market.marketuz.service.impl;

import lombok.RequiredArgsConstructor;
import market.marketuz.dto.CheckListDto;
import market.marketuz.dto.ProductDto;
import market.marketuz.dto.ResponseDto;
import market.marketuz.dto.UserDto;
import market.marketuz.enitity.User;
import market.marketuz.repository.UserRepository;
import market.marketuz.service.UserService;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final RestTemplate template;

    @Override
    public ResponseDto<UserDto> addMoney(Integer id, Double money){
        if(money == null || money <= 0){
            return ResponseDto.<UserDto>builder()
                    .code(-2).success(false).message("Money is below zero or equals to zero").build();
        }

        if(!repository.existsById(id)){

            return ResponseDto.<UserDto>builder()
                    .code(-1).success(false).message("User is not exists").build();
        }

        User user = repository.findById(id).get();
        user.setMoney(user.getMoney() + money);
        user = repository.save(user);

//        user = repository.findById(id).get();
        UserDto dto = UserDto.builder()
                .id(user.getId()).name(user.getName()).money(user.getMoney()).build();

        return ResponseDto.<UserDto>builder()
                .code(0).success(true).message("OK").data(dto).build();
    }

    public ResponseDto<UserDto> addUser(UserDto userDto){
        User user = User.builder()
                .id(userDto.getId()).name(userDto.getName()).money(userDto.getMoney()).build();

        if(repository.existsByName(user.getName())){
           return ResponseDto.<UserDto>builder()
                   .code(-3).success(false).message("User is already exists!").build();
        }

        user = repository.save(user);
        UserDto dto = UserDto.builder()
                .id(user.getId()).name(user.getName()).money(user.getMoney()).build();
        return ResponseDto.<UserDto>builder()
                .code(0).success(true).message("OK").data(dto).build();
    }

    public ResponseDto<Page<UserDto>> getAll(Integer p, Integer s){
        if(p == null || s == null){
            return ResponseDto.<Page<UserDto>>builder()
                    .code(-4).success(false).message("Page or size is null.").build();
        }

        PageRequest request = PageRequest.of(p, s);
        Page<UserDto> page = repository.findAll(request).map(
                u -> UserDto.builder()
                        .id(u.getId()).name(u.getName()).money(u.getMoney()).build()
        );

        return ResponseDto.<Page<UserDto>>builder()
                .code(0).success(true).message("OK").data(page).build();
    }

    public ResponseDto<ProductDto> getProduct(Integer userId, Integer productId, Integer amount){
        if(!repository.existsById(userId)){
            return ResponseDto.<ProductDto>builder()
                    .code(-1).success(false).message("User is not exists.").build();
        }

        String request = String.format("http://192.168.5.22:8080/order?userId=%d&productId=%d&amount=%d", userId, productId, amount);

        ResponseEntity<ResponseDto> entity = template.exchange(request, HttpMethod.GET, null, ResponseDto.class);
//        template.postForEntity();

        return entity.getBody();
    }


    public ResponseDto<Page<ProductDto>> getAllProduct(Integer page, Integer size){

        String request = String.format("http://192.168.5.22:8080/product/all?page=%d&size=%d", page, size);
        ResponseEntity<ResponseDto> entity = template.exchange(request, HttpMethod.GET, null, ResponseDto.class);
        return entity.getBody();
    }

    public ResponseDto<List<ProductDto>> getAllProductWithOutPage(){
        String request = String.format("http://192.168.5.22:8080/product/all-product");
        ResponseEntity<ResponseDto> entity = template.exchange(request, HttpMethod.GET, null, ResponseDto.class);
        return entity.getBody();
    }

    public ResponseDto<CheckListDto> buyProducts(Integer id) throws URISyntaxException {
        String request = String.format("http://192.168.5.22:8080/");
        if(!repository.existsById(id)){
            return ResponseDto.<CheckListDto>builder()
                    .code(-1).success(false).message("User is not exists").build();
        }
        User user = repository.findById(id).get();
        UserDto userDto = UserDto.builder()
                .id(user.getId()).name(user.getName()).money(user.getMoney()).build();

        ResponseEntity<ResponseDto> entity = template.postForEntity(new URI(request), userDto, ResponseDto.class);
        ResponseDto responseDto = entity.getBody();

        if (responseDto.getData() == null){
            return responseDto;
        }

        return responseDto;


    }
}
