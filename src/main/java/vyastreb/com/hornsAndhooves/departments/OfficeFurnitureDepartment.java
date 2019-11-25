package vyastreb.com.hornsAndhooves.departments;

import vyastreb.com.hornsAndhooves.furnitures.Chair;
import vyastreb.com.hornsAndhooves.furnitures.Furniture;
import vyastreb.com.hornsAndhooves.furnitures.RockingChair;
import vyastreb.com.hornsAndhooves.furnitures.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OfficeFurnitureDepartment extends Department {

    public final static String name = "office";

    public static List<Furniture> officeFurniture = new ArrayList<>(
            Arrays.asList(new Table(), new Chair(), new RockingChair()));
}
