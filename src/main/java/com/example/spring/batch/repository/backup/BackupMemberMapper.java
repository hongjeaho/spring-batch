package com.example.spring.batch.repository.backup;

import com.example.spring.batch.dto.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BackupMemberMapper {

    void insertMember(MemberVO memberVO);
}
