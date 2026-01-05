package fr.isen.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MenuService {
    List<MenuItem> getAllMenuItems();
    MenuItem getMenuItemById(int id);
    List<MenuItem> getAvailableMenuItems();
}
