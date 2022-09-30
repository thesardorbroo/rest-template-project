package market.marketuz.controller;

import lombok.RequiredArgsConstructor;
import market.marketuz.dto.CheckListDto;
import market.marketuz.dto.ProductDto;
import market.marketuz.dto.ResponseDto;
import market.marketuz.dto.UserDto;
import market.marketuz.service.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl service;

    @PostMapping("/money")
    public ResponseDto<UserDto> addToMoney(@RequestParam Integer id, @RequestParam Double money){
        return service.addMoney(id, money);
    }

    @PostMapping
    public ResponseDto<UserDto> addUser(@RequestBody UserDto userDto){
        return service.addUser(userDto);
    }

    @GetMapping("/get-product")
    public ResponseDto<ProductDto> getProduct(@RequestParam Integer userId,
                                              @RequestParam Integer productId,
                                              @RequestParam Integer amount){
        return service.getProduct(userId, productId, amount);
    }

    @GetMapping("/buy-product")
    public ResponseDto<CheckListDto> buyProducts(@RequestParam Integer id) throws URISyntaxException {
        return service.buyProducts(id);
    }

    @GetMapping
    public ResponseDto<Page<UserDto>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return service.getAll(page, size);
    }

    @GetMapping("/all-product")
    public ResponseDto<Page<ProductDto>> getAllProduct(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return service.getAllProduct(page, size);
    }

}
