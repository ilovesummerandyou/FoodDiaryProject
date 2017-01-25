package chen.yuhuan.presentation;

import chen.yuhuan.data.Food;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Yuhuan Chen
 * @since Oct. 18, 2015
 */

// The Inut class is to get data from the broswer.
public class Input {

    public static Food getFoodInfo(HttpServletRequest request) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d = formatter.parse(request.getParameter("date"));

        String f = request.getParameter("food");
        String fg = request.getParameter("foodGroup");
        int s = Integer.parseInt(request.getParameter("servings"));
        return new Food(d, f, fg, s);
    }

    // This method is to adjust whether the food information is submitted or not.
    public static boolean foodInfoWasSubmitted(HttpServletRequest request) {
        String food = request.getParameter("food");
        return food != null;
    }
}
