package chen.yuhuan.application;

import chen.yuhuan.data.Database;
import chen.yuhuan.data.Food;
import chen.yuhuan.presentation.Input;
import chen.yuhuan.presentation.Output;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yuhuan Chen
 * @since Oct. 18, 2015
 */
// The ShowFoods class to display all the information needed from the database to screen.
public class ShowFoods extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Database db = new Database()) {
            if (Input.foodInfoWasSubmitted(request)) {
                Food newFood = null;
                try {
                    newFood = Input.getFoodInfo(request);
                } catch (ParseException ex) {
                    Logger.getLogger(ShowFoods.class.getName()).log(Level.SEVERE, null, ex);
                }
                db.addFood(newFood);
            }
            Collection<Food> foods = db.getFoods();
            Output.tableOfFoods(response, foods);
        } catch (SQLException ex) {
            Output.errorMessage(response, ex);
            throw new ServletException(ex);//get to glassfish log file
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Food diary";
    }// </editor-fold>

}
