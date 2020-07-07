package com.udacity.jwdnd.course1.cloudstorage.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;

@Mapper
public interface CredentialMapper {

	@Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    public List<Credential> getCredentialByUser(int userId);
	
	@Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialId")
	int insert(Credential credential);

	@Delete("DELETE FROM CREDENTIALS where credentialid = #{credentialId}")
	void deleteCredential(Integer credentialId);

	@Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key= #{key}, password = #{password} WHERE credentialid = #{credentialId}")
    public int updateCredential(Credential credential);
}
