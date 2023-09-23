package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        //User user = UserMapper.mapToUser(userDto);
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        //return UserMapper.mapToUserDto(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        //return UserMapper.mapToUserDto(optionalUser.get());
        return modelMapper.map(optionalUser.get(), UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        //return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());

        return users.stream().map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User curUser = userRepository.findById(userDto.getId()).get();
        curUser.setFirstName(userDto.getFirstName());
        curUser.setLastName(userDto.getLastName());
        curUser.setEmail(userDto.getEmail());
        userRepository.save(curUser);
        //return UserMapper.mapToUserDto(curUser);
        return modelMapper.map(curUser, UserDto.class);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

}
