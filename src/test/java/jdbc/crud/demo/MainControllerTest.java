package jdbc.crud.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Test
    public void homeMessage() {
    }

    @Test
    public void newStock() {

    }

    @Test
    public void changeStock() {
    }
}