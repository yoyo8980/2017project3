package com.hb.major.controller;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hb.major.model.bbs.BbsDao;
import com.hb.major.model.entity.BbsVo;
import com.hb.major.model.entity.UserVo;
import com.hb.major.service.bbs.BbsService;
import com.hb.major.service.user.UserService;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private BbsService bbsService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale) {
		logger.info("The client locale is {}.", locale);

		return "main";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String login(@ModelAttribute UserVo bean, HttpServletRequest req) throws Exception {

		req.setCharacterEncoding("UTF-8");

//		if (!(req.getParameter("kakao_id").equals(bean.getKakao_id()))) {
			// kakao_id PK라 동일한 값이 들어오면 원래 입력은 안됨..
			bean.setKakao_id(req.getParameter("kakao_id"));
			bean.setKakao_nick(req.getParameter("kakao_nick"));
			bean.setKakao_profile_image(req.getParameter("kakao_profile_image"));
			bean.setKakao_thumbnail_image(req.getParameter("kakao_thumbnail_image"));
			userService.userInsertOne(bean);
			System.out.println(bean);
			System.out.println("입력");
//			} else {
//				System.out.println("아이디 있으니께 메인으로 리턴");
//				return "main";
//			}
		return "main";
	}

	@RequestMapping(value = "/userdelete", method = RequestMethod.GET)
	public String unlinkPage() {
		System.out.println("앱 연결 해제 페이지 이동 (회원 탈퇴)");
		return "loginoutdel/userdelete";
	}
		
	@RequestMapping(value = "/userdelete", method = RequestMethod.DELETE)
	public String unlink(@ModelAttribute UserVo bean, HttpServletRequest req) {
		// db에서 아이디 삭제해야하니까 post방식으로
		String delid = req.getParameter("delid");
		//String no = req.getParameter("kakao_id");
		
		
		System.out.println("탈퇴 들어옴");
		
		System.out.println(delid);
		userService.userDeleteOne(delid);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/deleteing", method = RequestMethod.GET)
	public String deleteing() {
		System.out.println("앱 연결 해제 페이지 이동 (회원 탈퇴)");
		return "redirect:/";
	}

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String board(Locale locale, Model model) {

		logger.info("게시판", locale);
		//조회하려는페이지
		bbsService.bbsListAll(model);
		
		return "user/board";
	}

	@RequestMapping(value = "/board/{bbspage}", method = RequestMethod.GET)
	public String board(Locale locale, Model model, @PathVariable("bbspage") String bbspage) {

		logger.info("게시판", locale);
		System.out.println("게시판 페이지는 " + bbspage);
		bbsService.bbsListAll(model);
		System.out.println("게시판페이징"+model);
		model.addAttribute("pnum", bbspage);
		int startpage=0;
		int endpage=0;
		int page=0;
		
		try {
			startpage=(Integer.parseInt(bbspage)-1);
			endpage=startpage+5-1;
			
			if( bbspage!=null && bbspage!="") {
				if(!bbspage.equals("1")) {
					int temp=(Integer.parseInt(bbspage)-1)*10;
					page=temp;
				
				}else if(bbspage.equals("1")) {
					page=0;
				}
			}
		}catch(Exception e) {
			return "redirect:/board";
		}
		
		
		return "user/board";
	}

	@RequestMapping(value = "/detail/{postno}", method = RequestMethod.GET)
	public String detail(Locale locale, Model model, @PathVariable("postno") int no) {

		logger.info("게시글 상세 페이지", locale);
		bbsService.bbsDetailOne(no, model);

		return "user/detail";
	}

	@RequestMapping(value = "/edit/{postno}", method = RequestMethod.GET)
	public String editpage(Locale locale, Model model, @PathVariable("postno") int no) {

		logger.info("게시글 수정 페이지", locale);
		bbsService.bbsDetailOne(no, model);

		return "user/edit";
	}

	@RequestMapping(value = "/edit/{postno}", method = RequestMethod.PUT)
	public String edit(@PathVariable("postno") int no, BbsVo bean, Locale locale) throws Exception {
		bbsService.bbsUpdateOne(bean);
		System.out.println("내용 수정");
		return "redirect:/board";
	}

	@RequestMapping(value = "/delete/{postno}", method = RequestMethod.GET)
	public String delete(@PathVariable("postno") int no, Model model) throws Exception {
		bbsService.bbsDetailOne(no, model);
		//System.out.println(bean+"삭제");
		return "user/delete";
	}
	@RequestMapping(value = "/delete/{postno}", method = RequestMethod.DELETE)
	public String deletecompl(@PathVariable("postno") int no, BbsVo bean, Locale locale) throws Exception {
		bbsService.bbsDeleteOne(no);
		System.out.println(bean+"삭제");
		return "redirect:/board";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeadd(Locale locale, @ModelAttribute BbsVo bean, HttpServletRequest req) {
		
		logger.info("게시글 작성", locale);
		System.out.println("add 포스트 들어옴");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bbsService.bbsAddOne(bean);
		System.out.println(bean);

		return "redirect:/board";
	}

	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	public String aboutus(Locale locale, Model model) throws Exception {
		logger.info("ABOUT US", locale);

		return "user/aboutus";
	}

	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String notice(Locale locale, Model model) throws Exception {
		logger.info("공지사항", locale);

		return "user/notice";
	}

	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String complaint(Locale locale, Model model) throws Exception {
		logger.info("문의사항", locale);

		return "user/question";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Locale locale, Model model) {
		logger.info("게시글 작성페이지", locale);
		System.out.println("add 겟 들어옴");
		//카카오닉네임받아와서 글쓰기시 이름에 닉네임 떠야함.
		return "user/write";
	}

}