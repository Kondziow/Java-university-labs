import org.example.Mage;
import org.example.MageController;
import org.example.MageRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MageControllerTest {

    @Test
    public void delete_DeleteExistingObject_ReturnDone() {
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        assertEquals("done", mageController.delete("aaa"), "should be \"done\"");
    }

    @Test
    public void delete_DeleteNotExistingObject_ReturnNotFound() {
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        doThrow(new IllegalArgumentException()).when(mageRepository).delete(any());

        assertEquals("not found", mageController.delete("aaa"), "should be \"not found\"");
    }

    @Test
    public void find_GetNotExistingObject_ReturnNotFound() {
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        when(mageRepository.find("aaa")).thenReturn(Optional.empty());

        assertEquals("not found", mageController.find("aaa"), "should be \"not found\"");
    }

    @Test
    public void find_GetExistingObject_ReturnObjectToString() {
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        Mage mage = new Mage("aaa", 10);
        when(mageRepository.find("aaa")).thenReturn(Optional.of(mage));

        assertEquals(mage.toString(), mageController.find("aaa"), "should be \"mage toString\"");
    }

    @Test
    public void save_SaveObjectWithCorrectParameter_ReturnDone() {
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        assertEquals("done", mageController.save("aaa", "5"), "should be \"done\"");
    }

    @Test
    public void save_SaveObjectWithIncorrectParameter_ReturnBadRequest() {
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        doThrow(new IllegalArgumentException()).when(mageRepository).save(any(Mage.class));

        assertEquals("bad request", mageController.save("aaa", "5"), "should be \"bad request\"");
    }
}
