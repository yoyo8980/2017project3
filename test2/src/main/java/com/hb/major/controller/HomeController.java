package com.hb.major.controller;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
			// kakao_id PK�씪 �룞�씪�븳 媛믪씠 �뱾�뼱�삤硫� �썝�옒 �엯�젰�� �븞�맖..
			bean.setKakao_id(req.getParameter("kakao_id"));
			bean.setKakao_nick(req.getParameter("kakao_nick"));
			bean.setKakao_profile_image(req.getParameter("kakao_profile_image"));
			bean.setKakao_thumbnail_image(req.getParameter("kakao_thumbnail_image"));
			userService.userInsertOne(bean);
			System.out.println(bean);
			System.out.println("�엯�젰");
		return "main";
	}

	@RequestMapping(value = "/deleteaccount", method = RequestMethod.GET)
	public String unlinkPage() {
		System.out.println("�빋 �뿰寃� �빐�젣 �럹�씠吏� �씠�룞 (�쉶�썝 �깉�눜)");
		return "loginoutdel/deleteaccount";
	}
	
	@RequestMapping(value = "/deleteaccount", method = RequestMethod.POST)
	public String unlink(@ModelAttribute UserVo bean, HttpServletRequest req) {
		String no = req.getParameter("kakao_id");
		System.out.println(no);
		userService.userDeleteOne(no);
		return "redirect:/main";
	}

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String board(Locale locale, Model model) {

		logger.info("寃뚯떆�뙋", locale);

		bbsService.bbsListAll(model);

		return "user/board";
	}

	@RequestMapping(value = "/board/{bbspage}", method = RequestMethod.GET)
	public String board(Locale locale, Model model, @PathVariable("bbspage") int bbspage) {

		logger.info("寃뚯떆�뙋", locale);
		System.out.println("寃뚯떆�뙋 �럹�씠吏��뒗 " + bbspage);
		bbsService.bbsListAll(model);

		return "user/board";
	}

	@RequestMapping(value = "/detail/{postno}", method = RequestMethod.GET)
	public String detail(Locale locale, Model model, @PathVariable("postno") int no) {

		logger.info("寃뚯떆湲� �긽�꽭 �럹�씠吏�", locale);
		bbsService.bbsDetailOne(no, model);

		return "user/detail";
	}

	@RequestMapping(value = "/edit/{postno}", method = RequestMethod.GET)
	public String editpage(Locale locale, Model model, @PathVariable("postno") int no) {

		logger.info("寃뚯떆湲� �닔�젙 �럹�씠吏�", locale);
		bbsService.bbsDetailOne(no, model);

		return "user/edit";
	}

	@RequestMapping(value = "/edit/{postno}", method = RequestMethod.PUT)
	public String edit(@PathVariable("postno") int no, BbsVo bean, Locale locale) throws Exception {
		bbsService.bbsUpdateOne(bean);
		System.out.println("�궡�슜 �닔�젙");
		return "redirect:/board";
	}

	@RequestMapping(value = "/delete/{postno}", method = RequestMethod.POST)
	public String delete(@PathVariable("postno") int no, BbsVo bean, Locale locale) throws Exception {
		bbsService.bbsDeleteOne(no);
		System.out.println(bean+"�궘�젣");
		return "redirect:/board";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeadd(Locale locale, @ModelAttribute BbsVo bean, HttpServletRequest req) {

		logger.info("寃뚯떆湲� �옉�꽦", locale);
		System.out.println("add �룷�뒪�듃 �뱾�뼱�샂");
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
		logger.info("怨듭��궗�빆", locale);

		return "user/notice";
	}

	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String complaint(Locale locale, Model model) throws Exception {
		logger.info("臾몄쓽�궗�빆", locale);

		return "user/question";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Locale locale, Model model) {
		logger.info("寃뚯떆湲� �옉�꽦�럹�씠吏�", locale);
		System.out.println("add 寃� �뱾�뼱�샂");
		return "user/write";
	}

}