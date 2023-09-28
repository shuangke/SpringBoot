package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
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
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistException("email: " + userDto.getEmail() + " already exists");
        }
        //User user = UserMapper.mapToUser(userDto);
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        //return UserMapper.mapToUserDto(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("UserId", "id", userId)
        );
        //return UserMapper.mapToUserDto(optionalUser.get());
        return modelMapper.map(user, UserDto.class);
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
        User curUser = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("UserId", "id", userDto.getId())
        );
        curUser.setFirstName(userDto.getFirstName());
        curUser.setLastName(userDto.getLastName());
        curUser.setEmail(userDto.getEmail());
        userRepository.save(curUser);
        //return UserMapper.mapToUserDto(curUser);
        return modelMapper.map(curUser, UserDto.class);
    }

    @Override
    public void deleteUserById(Long userId) {
        User curUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("UserId", "id", userId)
        );
        userRepository.deleteById(userId);
    }

}
