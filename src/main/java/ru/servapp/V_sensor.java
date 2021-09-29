package ru.servapp;

import lombok.Data;
import ru.servapp.enums.ReleType;
import ru.servapp.enums.SensorType;

@Data
public class V_sensor {
    private int sensor_id;

    private int number;

//    private float temp;
//    private float hum;

    private double min_temp;
    private double max_temp;


    private double preset_temp;
    private double preset_hum;

    private SensorType sensorType;
    private ReleType releType;

    public V_sensor(int sensor_id, double min_temp, double max_temp) {
        this.sensor_id = sensor_id;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.number = (sensor_id & ~248);
        int type = (sensor_id & ~231) >> 3;

        switch (type) {
            case (0):
                this.sensorType = SensorType.TYPE1;
                break;
            case (1):
                this.sensorType = SensorType.TYPE2;
                break;
            case (2):
                this.sensorType = SensorType.TYPE3;
                break;
            default:
                this.sensorType = SensorType.TYPE4;
                break;
        }

        int rele = (sensor_id & ~223) >> 5;
        switch (rele) {
            case (1):
                this.releType = ReleType.Rele;
                break;
            default:
                this.releType = ReleType.NO_Rele;
                break;

        }
    }

    public double getTemp() {
        return min_temp + Math.random() * (max_temp - min_temp);
    }
}
