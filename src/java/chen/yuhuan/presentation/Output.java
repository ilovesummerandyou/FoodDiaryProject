package chen.yuhuan.presentation;

import chen.yuhuan.data.Database;
import chen.yuhuan.data.Food;
import chen.yuhuan.data.GroupPercentage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yuhuan Chen
 * @since Oct. 18, 2015
 */
// The Output class is for displaying response into broswer.
public class Output {

    /**
     * A template that will generate an HTML document for any content.
     *
     * @param response
     * @param content the actual HTML content of the page
     * @throws IOException
     */
    static void writeHTML(HttpServletResponse response,
            String content) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' type='text/css' href='fooddiary.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println(content);
            out.println("</body>");
            out.println("</html>");
        }
    }

    /*
     Given a collection of {@link Food} objects, produce an HTML table that contains all of their information
    
     @param response
     @param foods
     */
    public static void tableOfFoods(HttpServletResponse response, Collection<Food> foods) throws IOException, SQLException {
        StringBuilder table = new StringBuilder();
        table.append("<table id='tableOfFoods'>");
        table.append("<tr><th colSpan='4' class='tableHeader'>Food Diary List</th></tr>");
        table.append("<tr><th>Date</th><th>Food</th><th>FoodGroup</th><th>Servings</th></tr>");
        for (Food f : foods) {
            table.append("<tr>");
            table.append("<td>");
            table.append(f.getDate());
            table.append("</td>");
            table.append("<td>");
            table.append(f.getFood());
            table.append("</td>");
            table.append("<td>");
            table.append(f.getFoodGroup());
            table.append("</td>");
            table.append("<td>");
            table.append(f.getServings());
            table.append("</td>");
            table.append("</tr>");
        }
        makeForm(table);
        showReport(table);
        table.append("</table>");
        writeHTML(response, table.toString());
    }

    /*
     This method is to collect data from users submitted to add into the FoodDiary table.
    
     @param foodAddingForm
     */
    static void makeForm(StringBuilder foodAddingForm) {
        foodAddingForm.append("<form method='GET' action='ShowFoods'>");
        foodAddingForm.append("<tr>");
        foodAddingForm.append("<td><input type='Date' name='date' required></td>");
        foodAddingForm.append("<td><input type='text' name='food' required></td>");
        foodAddingForm.append("<td><select name='foodGroup'>\n"
                + "                        <option>Fruits & Vegetables</option>\n"
                + "                        <option>Grain Products</option>\n"
                + "                        <option>Milk & Alternatives</option>\n"
                + "                        <option>Meat & Alternatives</option>\n"
                + "                    </select></td>");
        foodAddingForm.append("<td><input type='number' name='servings' required></td>");
        foodAddingForm.append("</tr>");
        foodAddingForm.append("<tr>");
        foodAddingForm.append("<td colSpan='4' id='subButton'><input type='submit' value='Add Food'></td>");
        foodAddingForm.append("</tr>");
        foodAddingForm.append("</form>");
    }

    /*
     This method is to display the FoodGroups and their percantage based on the FoodDiary table.
    
     @param reportTable
     */
    static void showReport(StringBuilder reportTable) throws SQLException {
        reportTable.append("<table id='showReportTable'>");
        reportTable.append("<tr><th colSpan='2' class='tableHeader'>Food Diary Analysis Report</th></tr>");
        reportTable.append("<tr><th>");
        reportTable.append("Food Group");
        reportTable.append("</th>");
        reportTable.append("<th>");
        reportTable.append("Percentage");
        reportTable.append("</th></tr>");
        Database db = new Database();
        int totalServings = db.getTotalServings();
        Collection<GroupPercentage> gn = db.getGroupReports();
        for (GroupPercentage n : gn) {
            reportTable.append("<tr>");
            reportTable.append("<td>");
            reportTable.append(n.getFoodGroup());
            reportTable.append("</td>");
            reportTable.append("<td>");
            reportTable.append(n.getGroupServingsPercentageString());
            reportTable.append("</td>");
            reportTable.append("</tr>");
        }
        reportTable.append("</table>");
    }

    /*
     The error message to show error if any related to the database.
    
     @param response
     @param e
     */
    public static void errorMessage(HttpServletResponse response, Exception e) throws IOException {
        writeHTML(response, "<p>There was a problem " + e.getMessage() + "</p>");
    }
}
