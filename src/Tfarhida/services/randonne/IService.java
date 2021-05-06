/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.services.randonne;

import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author PC
 */
public interface IService<T> {
    boolean insert(T t);
    boolean update(T t);
    boolean delete(int id);
    List<T> displayAll();
    T findById(int id);
    
 
    
}
