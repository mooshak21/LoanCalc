package tiles;

import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.Attribute;

public class LoanViewPreparer implements ViewPreparer {

    public void execute(Request tilesRequest, AttributeContext attributeContext)
    throws PreparerException {
        attributeContext.putAttribute(
            "body",
            new Attribute("This is the value added by the ViewPreparer"));
    }
}