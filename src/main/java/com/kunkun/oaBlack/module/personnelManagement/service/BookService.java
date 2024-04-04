package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddBookDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.BookEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingAndBookVo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BookService extends IService<BookEntity> {
    NoticeEntity addBook(AddBookDao bookDao, Authentication authentication);
}
