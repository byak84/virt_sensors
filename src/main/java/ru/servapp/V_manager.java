package ru.servapp;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class V_manager {
    private final List<V_sensor> v_sens_array = new ArrayList<>();

    public void create_VSensors(int sensor_id, double min_temp, double max_temp) throws Exception {
        V_sensor v_sensor = getByID(sensor_id);
        if (v_sensor == null) v_sens_array.add(new V_sensor(sensor_id, min_temp, max_temp));
        else {
            throw new Exception("Такой датчик уже есть");
        }
    }

    public V_sensor getByID(int sensor_id) {
        V_sensor tmp = null;
        for (V_sensor v_sensor : v_sens_array) {
            if (v_sensor.getSensor_id() == sensor_id) {
                tmp = v_sensor;
                break;
            }
        }
        return tmp;
    }

    public char[] getVSensorInfo(int sensor_id) {
        V_sensor v_sensor = getByID(sensor_id);
        if (v_sensor != null) {
            System.out.println(v_sensor.getTemp());
        }
        return null;
    }

}
