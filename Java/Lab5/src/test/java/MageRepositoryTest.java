import org.example.Mage;
import org.example.MageRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MageRepositoryTest {

    @Test
    public void delete_DeleteNotExistingObject_ThrowIllegalArgumentException() {
        MageRepository mr = new MageRepository();
        assertThrows(
                IllegalArgumentException.class,
                () -> mr.delete("aaa")
        );
    }

    @Test
    public void find_GetNotExistingObject_ReturnEmptyOptionalObject() {
        MageRepository mr = new MageRepository();
        Optional value = mr.find("aaa");
        assertEquals(value, Optional.empty());
    }

    @Test
    public void find_GetExistingObject_ReturnNotEmptyOptionalObject() {
        MageRepository mr = new MageRepository();
        mr.save(new Mage("aaa", 5));
        Optional value = mr.find("aaa");
        assertNotEquals(value, Optional.empty());
    }

    @Test
    public void save_SaveMageWithAlreadyTakedName_ThrowIllegalArgumentException() {
        MageRepository mr = new MageRepository();
        mr.save(new Mage("aaa", 5));
        assertThrows(
                IllegalArgumentException.class,
                () -> mr.save(new Mage("aaa", 5))
        );
    }
}
