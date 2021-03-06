package samlen.tsoi.showcase.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import samlen.tsoi.showcase.web.dao.UserMapper;
import samlen.tsoi.showcase.web.entity.po.User;
import samlen.tsoi.showcase.web.service.UserWriteService;

/**
 * @author samlen_tsoi
 * @date 2017/12/1
 */
@Service
public class UserWriteServiceImpl implements UserWriteService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertOne(User user) {
        userMapper.insertSelective(user);
    }
}
