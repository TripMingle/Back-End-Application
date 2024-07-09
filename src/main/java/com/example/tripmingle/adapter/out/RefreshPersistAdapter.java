package com.example.tripmingle.adapter.out;

import com.example.tripmingle.entity.Refresh;
import com.example.tripmingle.port.out.RefreshPort;
import com.example.tripmingle.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshPersistAdapter implements RefreshPort {

    private final RefreshRepository refreshRepository;

    @Override
    public void save(Refresh refreshEntity) {
        refreshRepository.save(refreshEntity);
    }

    @Override
    public void deleteRefresh(String email) {
        refreshRepository.deleteByEmail(email);
    }
}
