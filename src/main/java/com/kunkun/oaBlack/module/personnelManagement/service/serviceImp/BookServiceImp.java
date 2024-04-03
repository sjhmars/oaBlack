package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.BookEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.BookMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImp extends ServiceImpl<BookMapper, BookEntity> implements BookService {
}
