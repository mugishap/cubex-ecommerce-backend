package com.cubex.ecommerce.v1.controllers;

import com.cubex.ecommerce.v1.dtos.SignUpDTO;
import com.cubex.ecommerce.v1.fileHandling.File;
import com.cubex.ecommerce.v1.fileHandling.FileStorageService;
import com.cubex.ecommerce.v1.models.Cart;
import com.cubex.ecommerce.v1.models.Role;
import com.cubex.ecommerce.v1.models.User;
import com.cubex.ecommerce.v1.repositories.ICartRepository;
import com.cubex.ecommerce.v1.repositories.IRoleRepository;
import com.cubex.ecommerce.v1.repositories.IUserRepository;
import com.cubex.ecommerce.v1.serviceImpls.UserServiceImpl;
import com.cubex.ecommerce.v1.utils.Constants;
import com.cubex.ecommerce.v1.exceptions.BadRequestException;
import com.cubex.ecommerce.v1.payload.ApiResponse;
import com.cubex.ecommerce.v1.security.JwtTokenProvider;
import com.cubex.ecommerce.v1.services.IFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;
    private final IUserRepository userRepository;
    private static final ModelMapper modelMapper = new ModelMapper();
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileStorageService fileStorageService;
    private final IFileService fileService;
    private final ICartRepository cartRepository;

    @Value("${uploads.directory.user_profiles}")
    private String directory;

    @Autowired
    public UserController(UserServiceImpl userService, IUserRepository userRepository, IRoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider,
                          FileStorageService fileStorageService, IFileService fileService, ICartRepository cartRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.fileService = fileService;
        this.fileStorageService = fileStorageService;
        this.cartRepository = cartRepository;
    }

    @GetMapping(path = "/current-user")
    public ResponseEntity<ApiResponse> currentlyLoggedInUser() {
        return ResponseEntity.ok(new ApiResponse(true, userService.getLoggedInUser()));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAll();
    }

    @GetMapping(path = "/paginated")
    public Page<User> getAllUsers(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                                  @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return userService.getAll(pageable);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(this.userService.getById(id));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid SignUpDTO dto) {
        User user = new User();

        Optional<User> otherUser = this.userRepository.findByEmailOrMobile(user.getEmail(), dto.getMobile());
        if (otherUser.isPresent()) {
            if (otherUser.get().getEmail().equals(dto.getEmail())) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "User with that email already exists"));
            } else if (otherUser.get().getMobile().equals(dto.getMobile())) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "User with that telephone already exists"));
            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        Role role = roleRepository.findByName(dto.getRole()).orElseThrow(
                () -> new BadRequestException("User Role not set"));

        System.out.println("Before Setting");

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());
        user.setMobile(dto.getMobile());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(role));

        System.out.println("After setters");

        User entity = this.userService.create(user);

        Cart userCart = new Cart(entity);
        this.cartRepository.save(userCart);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users/create").toString());
        return ResponseEntity.created(uri).body(new ApiResponse(true, entity));
    }

    @PutMapping(path = "/{id}/upload-profile")
    public ResponseEntity<ApiResponse> uploadProfileImage(
            @PathVariable(value = "id") UUID id,
            @RequestParam("file") MultipartFile document
    ) {
        this.userService.getById(id);
        File file = this.fileService.create(document, directory);

        User updated = this.userService.changeProfileImage(id, file);

        return ResponseEntity.ok(new ApiResponse(true, "File saved successfully", updated));

    }

    @GetMapping("/load-file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> loadProfileImage(@PathVariable String filename) {

        Resource file = this.fileStorageService.load(directory, filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    private User convertDTO(SignUpDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}