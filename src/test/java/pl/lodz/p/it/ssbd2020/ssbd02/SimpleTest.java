package pl.lodz.p.it.ssbd2020.ssbd02;

import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangeOwnPasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import java.util.Date;

public class SimpleTest {
    @Test
    public void mapperTest(){
        UserDto userDto = new UserDto("test", "password", "email@google.com", false,
                true, new Date(),null,null,0,
                "Jacek","Placek","999888777");

        User user = ObjectMapperUtils.map(userDto, User.class);

        Assert.assertEquals(userDto.getFirstName(), user.getFirstName());
        Assert.assertEquals(user.getInvalidLoginAttempts(), 0);

        ChangeOwnPasswordDto changeOwnPasswordDto = new ChangeOwnPasswordDto();
        changeOwnPasswordDto.setOldPassword("oldPassword1@");
        changeOwnPasswordDto.setPassword("newPassword1@");
        changeOwnPasswordDto.setConfirmPassword("newPassword1@");

        User user1 = ObjectMapperUtils.map(changeOwnPasswordDto, User.class);

        Assert.assertNull(user1.getFirstName());
        Assert.assertEquals(user1.getPassword(), "newPassword1@");
    }

    @Test
    public void passwordHashTest(){
        String passwordCorrect = "zaq1@WSX";
        String passwordIncorrect = "zaq12wsx";

        BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();

        String passwrdHash = bCryptPasswordHash.generate(passwordCorrect.toCharArray());

        Assert.assertTrue(bCryptPasswordHash.verify(passwordCorrect.toCharArray(),passwrdHash));
        Assert.assertFalse(bCryptPasswordHash.verify(passwordIncorrect.toCharArray(),passwrdHash));
    }
}
