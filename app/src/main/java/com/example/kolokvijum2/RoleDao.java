package com.example.kolokvijum2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;
import java.util.List;

@Dao
public interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRole(Role role);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoles(List<Role> roles);

    @Query("SELECT * FROM roles ORDER BY id DESC")
    List<Role> getAllRoles();

    @Query("SELECT * FROM roles WHERE id = (SELECT MAX(id) FROM roles)")
    Role getRoleWithMaxId();

    @Query("DELETE FROM roles")
    void deleteAllRoles();

    @Query("SELECT * FROM roles WHERE id % 2 = 0 ORDER BY id")
    List<Role> getRolesWithEvenIds();
}
