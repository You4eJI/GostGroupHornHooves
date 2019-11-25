package vyastreb.com.hornsAndhooves.departments;

import vyastreb.com.hornsAndhooves.furnitures.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreSystemDepartment extends Department {

    public final static String name = "store";

    public static List<Furniture> storeFurniture = new ArrayList<>(
            Arrays.asList(new Cupboard(), new Nightstand(), new Shelf()));
}
