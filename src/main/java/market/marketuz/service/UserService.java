package market.marketuz.service;

import market.marketuz.dto.ResponseDto;
import market.marketuz.dto.UserDto;

public interface UserService {

    public ResponseDto<UserDto> addMoney(Integer id, Double money);
}
