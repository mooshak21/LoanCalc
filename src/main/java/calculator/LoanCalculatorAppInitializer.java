package calculator;
 
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
 
public class LoanCalculatorAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	 
	    @Override
	        protected Class<?>[] getRootConfigClasses() {
			        return new Class[] { LoanCalculatorAppConfiguration.class };
				    }
	      
	        @Override
		    protected Class<?>[] getServletConfigClasses() {
			            return null;
				        }
		  
		    @Override
		        protected String[] getServletMappings() {
				        return new String[] { "/calculateloan" };
					    }
		     
}
