package chen.yuhuan.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Yuhuan Chen
 * @since Oct 18, 2015
 */

//The Database class to add foods into the database and generate reports from it.
public class Database implements AutoCloseable {

    private static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/YuhuanFoodDiary";

    private Connection con;

    public Database() throws SQLException {
        con = DriverManager.getConnection(CONNECTION_STRING);
    }

    //Override the close() method in AutoCloseable to close the database connection.
    @Override
    public void close() throws SQLException {
        con.close();
    }

    // The method is to add food into the Food Diary List.
    public void addFood(Food food) throws SQLException {
        String sql = "INSERT INTO FOODDIARY (FoodDate,Description,FoodGroup,Servings) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(food.getDate().getTime()));
            stmt.setString(2, food.getFood());
            stmt.setString(3, food.getFoodGroup());
            stmt.setInt(4, food.getServings());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new SQLException("Excepted 1 row to be inserted, but " + rowsInserted + " rows were inserted");
            }
        }
    }

    // This method is to get foods from the FoodDiary table.
    public Collection<Food> getFoods() throws SQLException {
        String query = "SELECT * FROM FOODDIARY";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            try (ResultSet results = stmt.executeQuery()) {
                ArrayList<Food> foods = new ArrayList<>();
                while (results.next()) {
                    Date date = results.getDate("FoodDate");
                    String food = results.getString("Description");
                    String foodGroup = results.getString("FoodGroup");
                    int servings = results.getInt("servings");
                    Food f = new Food((java.sql.Date) date, food, foodGroup, servings);
                    foods.add(f);
                }
                return foods;
            }
        }
    }

    // This method is to get the total number of servings from the FoodDiary table.
    public int getTotalServings() throws SQLException {
        String qt = "SELECT SUM(Servings) AS totalServingNum FROM FOODDIARY";
        int total = 0;
        try (PreparedStatement stmt = con.prepareStatement(qt)) {
            try (ResultSet totalServings = stmt.executeQuery()) {
                while (totalServings.next()) {
                    total = totalServings.getInt("totalServingNum");
                }
                return total;
            }
        }
    }

    // This method is to get food reports of FoodGroups and their Percentages.
    public Collection<GroupPercentage> getGroupReports() throws SQLException {
        String q = "SELECT FoodGroup, SUM(Servings) as groupServingsSum \n"
                + " FROM FOODDIARY \n"
                + " GROUP BY FoodGroup";
        try (PreparedStatement stmt = con.prepareStatement(q)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<GroupPercentage> groupNumArray = new ArrayList<>();
                while (rs.next()) {
                    String fg = rs.getString("FoodGroup");
                    double gs = rs.getDouble("groupServingsSum");
                    double perventageNum = gs / this.getTotalServings() * 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    String formatNum = df.format(perventageNum);
                    String groupPercentage = formatNum + "%";
                    GroupPercentage fr = new GroupPercentage(fg, groupPercentage);
                    groupNumArray.add(fr);
                }
                return groupNumArray;
            }
        }
    }
    
}
