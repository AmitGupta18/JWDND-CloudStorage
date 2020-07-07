package com.udacity.jwdnd.course1.cloudstorage.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.data.File;

@Mapper
public interface FileMapper {

	@Select("SELECT * FROM FILES WHERE userid = #{userid}")
	List<File> getFilesByUser(Integer userid);

	@Select("SELECT * FROM FILES WHERE userid = #{userid} AND fileid = #{fileId}")
	File getFileByIdAndUser(Integer userid, Integer fileId);
	
	@Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userId, filedata) VALUES (#{fileId},#{filename},#{contenttype},#{filesize},#{userId}, #{filedata})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insert(File file);

	@Delete("DELETE FROM FILES where fileid = #{fileId}")
	void deleteFile(Integer fileId);

	@Select("SELECT filename from FILES WHERE userid = #{userid}")
	List<String> getFileNamesForUser(Integer userId);

}
