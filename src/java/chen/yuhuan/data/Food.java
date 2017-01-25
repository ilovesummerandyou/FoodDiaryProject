package chen.yuhuan.data;

import java.util.Date;

/**
 *
 * @author Yuhuan Chen
 * @since Oct. 18, 2015
 */

// The Food class to define Food instance with four parameters.
public class Food {

    private Date date;
    private String food;
    private String foodGroup;
    private int servings;

    public Food(Date date, String food, String foodGroup, int servings) {
        this.date = date;
        this.food = food;
        this.foodGroup = foodGroup;
        this.servings = servings;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = new java.sql.Date(date.getTime());
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

}
