package com.example.spring.batch.repository.store;

import com.example.spring.batch.dto.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberVO> selectAll();
}
