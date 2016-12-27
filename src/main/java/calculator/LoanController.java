package calculator;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Loan;

@RestController
public class LoanController {

    @RequestMapping("/loan")
    public Loan loan() {
            return new Loan();
    }

}