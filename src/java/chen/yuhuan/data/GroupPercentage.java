package chen.yuhuan.data;

/**
 *
 * @author Yuhuan Chen
 * @since Oct. 18, 2015
 */
// The GroupPercentage class is to get the foodgroups and their percantages.
public class GroupPercentage {

    private String foodGroup;
    private String groupServingsPercentageString;

    public GroupPercentage(String foodGroup, String groupServingsPercentageString) {
        this.foodGroup = foodGroup;
        this.groupServingsPercentageString = groupServingsPercentageString;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public String getGroupServingsPercentageString() {
        return groupServingsPercentageString;
    }

    public void setGroupServingsPercentageString(String groupServingsPercentageString) {
        this.groupServingsPercentageString = groupServingsPercentageString;
    }
}
