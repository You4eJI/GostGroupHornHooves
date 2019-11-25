package vyastreb.com.hornsAndhooves.departments;

import vyastreb.com.hornsAndhooves.furnitures.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoftFurnitureDepartment extends Department {

    public final static String name = "soft";

    public static List<Furniture> softFurniture = new ArrayList<>(
            Arrays.asList(new Bed(), new Sofa(), new Armchair()));
}
