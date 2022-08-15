package com.han.delivery.utils;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.han.delivery.config.auth.CustomUserDetails;

import lombok.Data;

@Data
public class Page {
	//비동기 무한스크롤
	private int view = 5;	// 화면에 출력할 목록 수	
	private int firstList;	// 페이지 첫번째 목록
	private int lastList;	// 페이지 마지막 목록
	//동기 페이지 버튼식
	private int pageCount = 5;	// 페이지 이동 버튼 갯수
	private int firstPage;	// 화면의 첫번째 페이지  1~5 => 1, 6~10 => 6
	private int lastPage;	// 화면의 마지막 페이지  1~5 => 5, 6~10 => 10
	private int prevPage;	// 이전페이지 버튼클릭
	private int nextPage;	// 다음페이지 버튼클릭
	private int nowPage;	// 현재페이지
	private int totalPage; // 총페이지 수
	
	public Page() {
		this(1);
	}
	
	public Page(int movePage) {
		page(movePage, view);
	}
	
	public Page(int movePage, int view) {
		page(movePage, view);
	}
	
	public Page(Integer page) {
		int movePage = 1;
		if(page != null) {
			movePage = page;
		}
		
		page(movePage, view);
	}
    
    
    public void page(int movePage, int view) {
		this.firstList = (view * movePage) - view + 1;
		this.lastList = movePage * view; 
		nowPage = movePage;
		firstPage = movePage - (movePage - 1) % pageCount;
		lastPage = firstPage + pageCount - 1;
		prevPage = firstPage - 1;
		nextPage = firstPage + pageCount;
	}
    
    public void totalPage(int listCount) {
        if(listCount % view == 0) {
            totalPage = listCount / view;
        } else {
            totalPage = listCount / view + 1;
        }
    }
}