package com.future.my.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.future.my.member.vo.MemberVO;

@Mapper
public interface IMemberDAO {
	
	// 회원가입
	public int registMember(MemberVO vo);
	// 회원조회
	public MemberVO loginMember(MemberVO vo);
	// 프로필 이미지 경로 저장
	public int profileUpload(MemberVO vo);
}
