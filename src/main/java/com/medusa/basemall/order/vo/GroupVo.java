package com.medusa.basemall.order.vo;

import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.promotion.entity.ActivityGroup;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.GroupMember;

import java.util.List;

public class GroupVo extends Order {

    ActivityGroup activityGroup;

    List<GroupMember> groupMembers;

    ActivityProduct activityProduct;

    public ActivityGroup getActivityGroup() {
        return activityGroup;
    }

    public void setActivityGroup(ActivityGroup activityGroup) {
        this.activityGroup = activityGroup;
    }

    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public ActivityProduct getActivityProduct() {
        return activityProduct;
    }

    public void setActivityProduct(ActivityProduct activityProduct) {
        this.activityProduct = activityProduct;
    }
}
