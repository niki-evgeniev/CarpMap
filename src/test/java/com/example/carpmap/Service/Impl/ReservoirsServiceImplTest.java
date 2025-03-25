import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.carpmap.Models.DTO.Reservoirs.ReservoirAllDTO;
import com.example.carpmap.Models.DTO.Reservoirs.ReservoirsAddDTO;
import com.example.carpmap.Models.Entity.Country;
import com.example.carpmap.Models.Entity.Fish;
import com.example.carpmap.Models.Entity.Reservoir;
import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Repository.CountryRepository;
import com.example.carpmap.Repository.FishRepository;
import com.example.carpmap.Repository.ReservoirRepository;
import com.example.carpmap.Repository.UserRepository;
import com.example.carpmap.Service.Impl.ReservoirsServiceImpl;
import com.example.carpmap.Service.PictureService;
import com.example.carpmap.Utility.ConvertorBgToEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservoirsServiceImplTest {

    @Mock
    private ReservoirRepository reservoirRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FishRepository fishRepository;
    @Mock
    private PictureService pictureService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ConvertorBgToEn convertorBgToEn;

    @InjectMocks
    private ReservoirsServiceImpl reservoirsService;

    private Reservoir testReservoir;
    private ReservoirsAddDTO testReservoirDTO;
    private UserDetails mockUserDetails;
    private User testUser;
    private Country testCountry;
    private Fish testFish1, testFish2;

    @BeforeEach
    void setUp() {
        // Инициализация на тестови обекти
        testReservoir = new Reservoir();
        testReservoir.setId(1L);
        testReservoir.setName("Язовир Тест");
        testReservoir.setDescription("Тестово описание");
        testReservoir.setUrlName("yazovir-test");

        testUser = new User();
        testUser.setUsername("testUser");

        testCountry = new Country();
        testCountry.setCountry("България");

        testFish1 = new Fish();
        testFish1.setFishName("Шаран");
        testFish2 = new Fish();
        testFish2.setFishName("Сом");

        testReservoirDTO = new ReservoirsAddDTO();
        testReservoirDTO.setName("Язовир Тест");
        testReservoirDTO.setDescription("Тестово описание");
        testReservoirDTO.setCountry("България");
        testReservoirDTO.setFishName(new String[]{"Шаран", "Сом"});
        testReservoirDTO.setUrlImage2("image2.jpg");
        testReservoirDTO.setUrlImage3("image3.jpg");
        testReservoirDTO.setUrlImage4("image4.jpg");

        mockUserDetails = mock(UserDetails.class);
    }

    @Test
    void testAddReservoirs_Success() {
        // Arrange
        when(countryRepository.findByCountry("България")).thenReturn(Optional.of(testCountry));
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(modelMapper.map(any(ReservoirsAddDTO.class), eq(Reservoir.class))).thenReturn(testReservoir);
        when(convertorBgToEn.convertCyrillicToLatin(anyString())).thenReturn("yazovir-test");
        when(mockUserDetails.getUsername()).thenReturn("testUser");
        when(fishRepository.findByFishName("Шаран")).thenReturn(Optional.of(testFish1));
        when(fishRepository.findByFishName("Сом")).thenReturn(Optional.of(testFish2));
        when(reservoirRepository.save(any(Reservoir.class))).thenReturn(testReservoir);
        boolean result = reservoirsService.addReservoirs(testReservoirDTO, mockUserDetails);
        assertTrue(result);
        verify(reservoirRepository).save(any(Reservoir.class));
        verify(pictureService).saveImages(anyList(), any(Reservoir.class));
    }

    @Test
    void testGetAllReservoirs_WithData() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Reservoir> reservoirPage = new PageImpl<>(List.of(testReservoir));
        ReservoirAllDTO reservoirAllDTO = new ReservoirAllDTO();
        
        when(reservoirRepository.findAll(pageable)).thenReturn(reservoirPage);
        when(modelMapper.map(any(Reservoir.class), eq(ReservoirAllDTO.class)))
                .thenReturn(reservoirAllDTO);
        Page<ReservoirAllDTO> result = reservoirsService.getAllReservoirs(pageable);
        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void testDeleteReservoir_Success() {
        when(reservoirRepository.findById(1L)).thenReturn(Optional.of(testReservoir));
        doNothing().when(pictureService).deleteAllListOfPicture(1L);
        reservoirsService.deleteReservoir(1L);
        verify(reservoirRepository).deleteById(1L);
        verify(pictureService).deleteAllListOfPicture(1L);
    }

    @Test
    void testCheckNameExisting_True() {
        when(reservoirRepository.findByName("Язовир Тест")).thenReturn(Optional.of(testReservoir));
        boolean exists = reservoirsService.checkNameExisting("Язовир Тест");
        assertTrue(exists);
    }
}