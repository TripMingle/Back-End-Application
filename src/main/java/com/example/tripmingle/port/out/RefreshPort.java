package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Refresh;

public interface RefreshPort {
    void save(Refresh refreshEntity);

    void deleteRefresh(String email);
}
