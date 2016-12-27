@RestController
public class LoanController {

    @RequestMapping("/loan")
    public Loan loan() {
            return new Loan();
    }

}