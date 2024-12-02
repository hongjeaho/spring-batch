package com.example.spring.batch.database.service;

import com.example.spring.backup.datasource.repository.BackupMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackupInsertService {

    private final BackupMemberMapper backupMemberMapper;


}
