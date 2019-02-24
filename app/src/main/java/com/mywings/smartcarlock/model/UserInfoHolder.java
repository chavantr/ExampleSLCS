package com.mywings.smartcarlock.model;

public class UserInfoHolder {


    private Car selectedCar;

    private User user;

    public static UserInfoHolder getInstance() {
        return UserInfoHolderHelper.INSTANCE;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class UserInfoHolderHelper {
        static UserInfoHolder INSTANCE = new UserInfoHolder();
    }

}
