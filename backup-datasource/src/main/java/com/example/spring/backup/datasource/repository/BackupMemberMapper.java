package com.example.spring.backup.datasource.repository;

import com.example.spring.backup.datasource.dto.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BackupMemberMapper {

    void insertMember(MemberVO memberVO);
}
