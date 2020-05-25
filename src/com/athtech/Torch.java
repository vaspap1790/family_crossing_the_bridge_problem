package com.athtech;

public class Torch {

    private int batteryLife;
    private TorchLocation location;

    public Torch() {
            this.batteryLife = 30;
            this.location = TorchLocation.SOURCE_BANK;
        }

    public Torch(Torch torch){
            this.batteryLife = torch.batteryLife;
            this.location = torch.location;
        }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public TorchLocation getLocation() {
        return location;
    }

    public void setLocation(TorchLocation location) {
        this.location = location;
    }
}
