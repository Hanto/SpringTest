package com.myrran.springtest;

import com.myrran.springtest.config.AppProperties;
import com.myrran.springtest.model.Group;
import com.myrran.springtest.model.User;
import com.myrran.springtest.model.dtos.GroupDTO;
import com.myrran.springtest.model.repo.GroupRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringtestApplicationTests
{
    private @Autowired GroupRepository groupRepository;
    private @Autowired ModelMapper modelMapper;
    private @Autowired AppProperties properties;

    @Test void contextLoads()
    {
        User user = new User();
        user.setName("Johana");

        Group group = new Group();
        group.setAddress("Obispo");
        group.setCity("Valencia");
        group.setCountry("Spain");
        group.setName("Nutri Group");
        group.setUser(user);

        GroupDTO dto = modelMapper.map(group, GroupDTO.class);

        System.out.println(dto.getUser().getName());
        System.out.println(dto.getUser().getId());


        Group group2 = modelMapper.map(dto, Group.class);

        System.out.println(group2.getName());


        System.out.println("ENV: "+ properties.getEnvironment());



    }
}
