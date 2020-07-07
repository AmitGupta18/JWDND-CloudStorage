package com.udacity.jwdnd.course1.cloudstorage.data.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;

@Mapper
public interface NoteMapper {

	@Select("SELECT * FROM NOTES WHERE userid = #{userid}")
	List<Note> getNotesByUser(Integer userid);

	@Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES (#{noteId},#{noteTitle},#{noteDescription},#{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int insert(Note note);

	@Delete("DELETE FROM NOTES where noteid = #{noteId}")
	void deleteNote(Integer noteId);

	@Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} where noteid = #{noteId} ")
	int updateNote(Note note);
}
